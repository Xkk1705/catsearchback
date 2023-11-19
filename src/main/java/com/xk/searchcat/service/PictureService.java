package com.xk.searchcat.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xk.searchcat.model.entity.Picture;

/**
 * 图片服务
 */
public interface PictureService {


    Page<Picture> listPictureByPage(long current, long size, String searchText);
}
