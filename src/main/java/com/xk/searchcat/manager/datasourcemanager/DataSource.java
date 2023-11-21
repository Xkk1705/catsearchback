package com.xk.searchcat.manager.datasourcemanager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * 数据源规范
 */
public interface DataSource<T> {
    Page<T> onSearch(long current, long size, String searchText, HttpServletRequest request);
}
