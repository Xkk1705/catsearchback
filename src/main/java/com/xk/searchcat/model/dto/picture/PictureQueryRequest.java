package com.xk.searchcat.model.dto.picture;

import com.xk.searchcat.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 创建请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PictureQueryRequest extends PageRequest implements Serializable {

    /**
     * 搜索关键字
     */
    private String searchText;

    private static final long serialVersionUID = 1L;
}