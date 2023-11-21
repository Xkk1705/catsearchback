package com.xk.searchcat.manager.datasourcemanager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xk.searchcat.model.entity.Picture;
import com.xk.searchcat.service.PictureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 图片数据源实现类
 */
@Service
public class PictureDataSource implements DataSource<Picture> {
    @Resource
    private PictureService pictureService;

    @Override
    public Page<Picture> onSearch(long current, long size, String searchText, HttpServletRequest request) {
        return pictureService.listPictureByPage(current, size, searchText);
    }

}






