package com.xk.searchcat.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xk.searchcat.common.ErrorCode;
import com.xk.searchcat.exception.ThrowUtils;
import com.xk.searchcat.model.dto.post.PostQueryRequest;
import com.xk.searchcat.model.dto.searchall.SearchAllQueryRequest;
import com.xk.searchcat.model.dto.user.UserQueryRequest;
import com.xk.searchcat.model.entity.Picture;
import com.xk.searchcat.model.entity.User;
import com.xk.searchcat.model.enums.SearchEnum;
import com.xk.searchcat.model.vo.PostVO;
import com.xk.searchcat.model.vo.SearchAllVO;
import com.xk.searchcat.model.vo.UserVO;
import com.xk.searchcat.service.PictureService;
import com.xk.searchcat.service.PostService;
import com.xk.searchcat.service.SearchAllService;
import com.xk.searchcat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帖子服务实现
 */
@Service
@Slf4j
public class SearchAllServiceImpl implements SearchAllService {
    @Resource
    private UserService userService;
    @Resource
    private PostService postService;
    @Resource
    private PictureService pictureService;


    @Override
    public SearchAllVO listSearchAllByPage(@RequestBody SearchAllQueryRequest searchAllQueryRequest, HttpServletRequest request) {
        String searchText = searchAllQueryRequest.getSearchText();
        long current = searchAllQueryRequest.getCurrent();
        long pageSize = searchAllQueryRequest.getPageSize();
        // 封装用户查询
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setCurrent(current);
        userQueryRequest.setPageSize(pageSize);
        // 查询用户
        Page<User> userPage = userService.listUserByPage(userQueryRequest);
        List<UserVO> userVOList = userService.getUserVO(userPage.getRecords());
        // 查询图片
        Page<Picture> picturePage = pictureService.listPictureByPage(current, pageSize, searchText);
        List<Picture> pictureList = picturePage.getRecords();
        // 封装帖子查询
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setCurrent(current);
        postQueryRequest.setPageSize(pageSize);
        postQueryRequest.setSearchText(searchText);
        Page<PostVO> postVOPage = postService.listPostListBypage(postQueryRequest, request);
        List<PostVO> postVOList = postVOPage.getRecords();

        SearchAllVO searchAllVO = new SearchAllVO();
        searchAllVO.setUserVOList(userVOList);
        searchAllVO.setPostVOList(postVOList);
        searchAllVO.setPictureList(pictureList);

        return searchAllVO;
    }

    @Override
    public SearchAllVO listSearchDiffererntKindByPage(SearchAllQueryRequest searchAllQueryRequest, HttpServletRequest request) {
        String type = searchAllQueryRequest.getType();
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        String searchText = searchAllQueryRequest.getSearchText();
        long current = searchAllQueryRequest.getCurrent();
        long pageSize = searchAllQueryRequest.getPageSize();
        //获取请求参数类别
        SearchEnum requestKind = SearchEnum.getEnumByValue(type);
        // 请求提取出的参数
        SearchAllVO searchAllVO = new SearchAllVO();
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        // 请求类被为空查询所有接口
        if (requestKind == null) {
            // 封装用户查询
            userQueryRequest.setUserName(searchText);
            userQueryRequest.setCurrent(current);
            userQueryRequest.setPageSize(pageSize);
            // 查询用户
            Page<User> userPage = userService.listUserByPage(userQueryRequest);
            List<UserVO> userVOList = userService.getUserVO(userPage.getRecords());
            // 查询图片
            Page<Picture> picturePage = pictureService.listPictureByPage(current, pageSize, searchText);
            List<Picture> pictureList = picturePage.getRecords();
            // 封装帖子查询
            postQueryRequest.setCurrent(current);
            postQueryRequest.setPageSize(pageSize);
            postQueryRequest.setSearchText(searchText);
            Page<PostVO> postVOPage = postService.listPostListBypage(postQueryRequest, request);
            List<PostVO> postVOList = postVOPage.getRecords();

            searchAllVO.setUserVOList(userVOList);
            searchAllVO.setPostVOList(postVOList);
            searchAllVO.setPictureList(pictureList);

        } else {
            switch (requestKind) {
                case POST:
                    // 封装帖子查询
                    postQueryRequest.setCurrent(current);
                    postQueryRequest.setPageSize(pageSize);
                    postQueryRequest.setSearchText(searchText);
                    Page<PostVO> postVOPage = postService.listPostListBypage(postQueryRequest, request);
                    List<PostVO> postVOList = postVOPage.getRecords();
                    searchAllVO.setPostVOList(postVOList);
                    break;
                case PICTURE:
                    Page<Picture> picturePage = pictureService.listPictureByPage(current, pageSize, searchText);
                    List<Picture> pictureList = picturePage.getRecords();
                    searchAllVO.setPictureList(pictureList);
                    break;
                case USER:
                    // 封装用户查询
                    userQueryRequest.setUserName(searchText);
                    userQueryRequest.setCurrent(current);
                    userQueryRequest.setPageSize(pageSize);
                    // 查询用户
                    Page<User> userPage = userService.listUserByPage(userQueryRequest);
                    List<UserVO> userVOList = userService.getUserVO(userPage.getRecords());
                    searchAllVO.setUserVOList(userVOList);
                    break;
                default:
            }

        }
        return searchAllVO;
    }
}




