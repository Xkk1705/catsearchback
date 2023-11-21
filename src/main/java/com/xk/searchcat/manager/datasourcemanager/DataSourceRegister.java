package com.xk.searchcat.manager.datasourcemanager;

import com.xk.searchcat.model.enums.SearchEnum;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 注册数据源
 */
@Component
public class DataSourceRegister {
    @Resource
    private PictureDataSource pictureDataSource;
    @Resource
    private PostDataSource postDataSource;
    @Resource
    private UserDataSource userDataSource;

    private Map<String, DataSource<T>> typeDataSourceMap;

    @PostConstruct
    public void doInit() {
        typeDataSourceMap = new HashMap() {{
            put(SearchEnum.USER.getValue(), userDataSource);
            put(SearchEnum.POST.getValue(), postDataSource);
            put(SearchEnum.PICTURE.getValue(), pictureDataSource);
        }};
    }

    public DataSource getTypeDataSource(String type) {
        if (typeDataSourceMap == null) {
            return null;
        }
        return typeDataSourceMap.get(type);
    }
}
