package com.xk.searchcat.model.vo;

import com.xk.searchcat.model.entity.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 聚合搜索
 */
@Data
public class SearchAllVO implements Serializable {
    private List<UserVO> userVOList;
    private List<PostVO> postVOList;
    private List<Picture> pictureList;
}
