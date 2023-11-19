package com.xk.searchcat.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xk.searchcat.exception.ThrowUtils;
import com.xk.searchcat.common.BaseResponse;
import com.xk.searchcat.common.ErrorCode;
import com.xk.searchcat.common.ResultUtils;
import com.xk.searchcat.model.dto.picture.PictureQueryRequest;
import com.xk.searchcat.model.entity.Picture;
import com.xk.searchcat.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 照片接口
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Resource
    private PictureService pictureService;


    /**
     * 分页获取列表（封装类）
     *
     * @param pictureQueryRequest;
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPostVOByPage(@RequestBody PictureQueryRequest pictureQueryRequest) {
        long current = pictureQueryRequest.getCurrent();
        long size = pictureQueryRequest.getPageSize();
        String searchText = pictureQueryRequest.getSearchText();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(pictureService.listPictureByPage(current, size, searchText));
    }


}
