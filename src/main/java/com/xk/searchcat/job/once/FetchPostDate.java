package com.xk.searchcat.job.once;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xk.searchcat.model.entity.Post;
import com.xk.searchcat.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

/**
 * 抓取文章内容
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class FetchPostDate implements CommandLineRunner {

    @Resource
    private PostService postService;

    @Override
    public void run(String... args) {
        ArrayList<Post> postArrayList = new ArrayList<>();
        String requestBody = "{\"current\":1,\"pageSize\":8,\"sortField\":\"_score\",\"sortOrder\":\"descend\",\"searchText\":\"java\",\"reviewStatus\":1}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String dataStrJson = HttpRequest.post(url)
                .body(requestBody)
                .execute().body();
        Map<String, Object> map = JSONUtil.toBean(dataStrJson, Map.class);
        JSONObject data = (JSONObject) map.get("data");
        JSONArray records = (JSONArray) data.get("records");
        for (Object record : records) {
            JSONObject recordTmp = (JSONObject) record;
            String content = (String) recordTmp.get("content");
            String title = (String) recordTmp.get("title");
            JSONArray tags = (JSONArray) recordTmp.get("tags");// 转换为形式
            String tagJson = JSONUtil.toJsonStr(tags);
            Post post = new Post();
            post.setUserId(1L);
            post.setContent(content);
            post.setTitle(title);
            post.setTags(tagJson);
            postArrayList.add(post);
        }
        boolean b = postService.saveBatch(postArrayList);
    }
}
