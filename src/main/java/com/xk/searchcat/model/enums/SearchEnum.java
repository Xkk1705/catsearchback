package com.xk.searchcat.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 聚合搜索枚举
 */
public enum SearchEnum {

    POST("帖子", "post"),
    PICTURE("照片", "picture"),
    USER("用户", "user");

    private final String text;

    private final String value;

    SearchEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }


    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static SearchEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {   return null;
        }

        for (SearchEnum anEnum : SearchEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
