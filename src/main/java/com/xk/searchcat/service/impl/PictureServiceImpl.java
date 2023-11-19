package com.xk.searchcat.service.impl;

import cn.hutool.json.JSONUtil;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.xk.searchcat.exception.BusinessException;
import com.xk.searchcat.common.ErrorCode;
import com.xk.searchcat.model.entity.Picture;

import com.xk.searchcat.service.PictureService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 图片服务实现
 */
@Service
public class PictureServiceImpl implements PictureService {


    @Override
    public Page<Picture> listPictureByPage(long current, long size, String searchText) {
        List<Picture> pictureList = new ArrayList<>();
        long pictureSize = (current - 1) * size;
        String url = String.format("https://cn.bing.com/images/search?q=%s&form=HDRSC2&first=%d", searchText, pictureSize);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Elements newsHeadlines = doc.select(".iuscp.isv");
        for (Element element : newsHeadlines) {
            //获取图片地址
            String m = element.select(".iusc").get(0).attr("m");
            Map bean = JSONUtil.toBean(m, Map.class);
            String mrul = (String) bean.get("murl");
            //获取title
            String title = element.select(".inflnk").get(0).attr("aria-label");
            Picture picture = new Picture();
            picture.setMurl(mrul);
            picture.setTitle(title);
            pictureList.add(picture);
            // 限制图像数量
            if (pictureList.size() >= size) {
                break;
            }
        }
        Page<Picture> picturePage = new Page<>(current, size);
        picturePage.setRecords(pictureList);
        return picturePage;
    }
}




