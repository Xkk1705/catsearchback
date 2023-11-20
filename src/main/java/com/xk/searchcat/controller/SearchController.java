package com.xk.searchcat.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xk.searchcat.common.BaseResponse;
import com.xk.searchcat.common.ErrorCode;
import com.xk.searchcat.common.ResultUtils;
import com.xk.searchcat.exception.ThrowUtils;
import com.xk.searchcat.model.dto.picture.PictureQueryRequest;
import com.xk.searchcat.model.dto.searchall.SearchAllQueryRequest;
import com.xk.searchcat.model.entity.Picture;
import com.xk.searchcat.model.vo.SearchAllVO;
import com.xk.searchcat.service.PictureService;
import com.xk.searchcat.service.SearchAllService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 聚合查询接口
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private SearchAllService searchAllService;


    /**
     * 聚合分页获取列表（封装类）
     *
     * @param searchAllQueryRequest;
     * @return
     */
    @PostMapping("/all")
    public BaseResponse<SearchAllVO> listSearchAllVOByPage(@RequestBody SearchAllQueryRequest searchAllQueryRequest, HttpServletRequest request) {
        long size = searchAllQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(searchAllService.listSearchAllByPage(searchAllQueryRequest,request));
    }

    @PostMapping("/kind")
    public BaseResponse<SearchAllVO> listSearchDifferentKindVOByPage(@RequestBody SearchAllQueryRequest searchAllQueryRequest, HttpServletRequest request) {
        long size = searchAllQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(searchAllService.listSearchDiffererntKindByPage(searchAllQueryRequest,request));
    }


}
