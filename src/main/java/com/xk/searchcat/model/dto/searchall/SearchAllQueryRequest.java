package com.xk.searchcat.model.dto.searchall;

import com.xk.searchcat.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 聚合搜索请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchAllQueryRequest extends PageRequest implements Serializable {

    /**
     * 搜索关键字
     */
    private String searchText;
    /**
     * 聚合搜索类别
     */
    private String type;

    private static final long serialVersionUID = 1L;
}