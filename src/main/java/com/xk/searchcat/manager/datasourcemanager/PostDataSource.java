package com.xk.searchcat.manager.datasourcemanager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xk.searchcat.model.dto.post.PostQueryRequest;
import com.xk.searchcat.model.entity.Post;
import com.xk.searchcat.model.vo.PostVO;
import com.xk.searchcat.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子数据源实现类
 */
@Service
@Slf4j
public class PostDataSource implements DataSource<PostVO> {
    @Resource
    private PostService postService;

    @Override
    public Page<PostVO> onSearch(long current, long size, String searchText, HttpServletRequest request) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setCurrent(current);
        postQueryRequest.setPageSize(size);
        postQueryRequest.setSearchText(searchText);
        Page<Post> postPage = postService.page(new Page<>(postQueryRequest.getCurrent(), postQueryRequest.getPageSize()),
                postService.getQueryWrapper(postQueryRequest));
        return postService.getPostVOPage(postPage, request);
    }
}




