//package com.czx.pojo;
//
//public class Role {
//    private long role_id;
//    private String role_name;
//    private String description;
//
//    public long getRole_id()
//    {
//        return role_id;
//    }
//    public String getRole_name()
//    {
//        return role_name;
//    }
//    public String getDescription()
//    {
//        return description;
//    }
//
//    public void setId(long id){
//        role_id=id;
//    }
//}

package com.czx.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Role implements Serializable {
    private Integer role_id;
    private String rolename;
    private String description;
    private Date createTime;
    private Date updateTime;
}