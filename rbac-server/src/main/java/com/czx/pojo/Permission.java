package com.czx.pojo;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Permission {
    @JsonProperty("permission_id")
    private long permission_id;
    @JsonProperty("permission_name")
    private String permission_name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("url")
    private String url;
    // 必须有一个无参数构造函数
    public Permission() {}
}
