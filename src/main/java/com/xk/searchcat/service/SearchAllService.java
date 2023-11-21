package com.xk.searchcat.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xk.searchcat.model.dto.searchall.SearchAllQueryRequest;
import com.xk.searchcat.model.entity.Picture;
import com.xk.searchcat.model.vo.SearchAllVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 聚合搜索服务
 */
public interface SearchAllService {



    @Deprecated
    SearchAllVO listSearchAllByPage(SearchAllQueryRequest searchAllQueryRequest, HttpServletRequest request);

    SearchAllVO listSearchDiffererntKindByPage(SearchAllQueryRequest searchAllQueryRequest, HttpServletRequest request);
}
