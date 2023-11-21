package com.xk.searchcat.manager.datasourcemanager;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xk.searchcat.model.dto.user.UserQueryRequest;
import com.xk.searchcat.model.entity.User;
import com.xk.searchcat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户数据源实现类
 */
@Service
@Slf4j
public class UserDataSource implements DataSource<User> {
    @Resource
    private UserService userService;


    @Override
    public Page<User> onSearch(long current, long size, String searchText, HttpServletRequest request) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setPageSize(size);
        userQueryRequest.setCurrent(current);
        return userService.page(new Page<>(current, size),
                userService.getQueryWrapper(userQueryRequest));
    }
}
