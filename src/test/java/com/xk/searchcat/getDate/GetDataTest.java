package com.xk.searchcat.getDate;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xk.searchcat.model.entity.Post;
import com.xk.searchcat.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@SpringBootTest
public class GetDataTest {
    @Resource
    private PostService postService;

    @Test
    void getPost() {
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
        Assertions.assertTrue(b);
    }

    @Test
    void getPicture() throws IOException {
        String searchText = "头像";
        String url = String.format("https://cn.bing.com/images/search?q=%s&form=HDRSC2&first=1", searchText);
        Document doc = Jsoup.connect(url).get();
        Elements newsHeadlines = doc.select(".iuscp.isv");
        for (Element element : newsHeadlines) {
            //获取图片地址
            String m = element.select(".iusc").get(0).attr("m");
            Map bean = JSONUtil.toBean(m, Map.class);
            String mrul = (String) bean.get("murl");
            //获取title
            String title = element.select(".inflnk").get(0).attr("aria-label");
            System.out.println(mrul);
            System.out.println(title);
        }
    }

}
