package com.czx.pojo;


import lombok.Data;

import java.util.Date;

@Data
public class User {
    private long user_id;
    private String username;

    private String name;
    private String password;

    private String phone;
    private String gender;
    private int status;
    private String email;
    private String address;
    private Date createTime;
    private Date updateTime;

    public String getPhone(){
        return phone;
    }


    public long getUser_id()
    {
        return user_id;
    }
    public String getName()
    {
        return name;
    }


    public String getGender()
    {
        return gender;
    }
    public int getStatus()
    {
        return status;
    }

    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }

    public void setId(long id){
        user_id=id;
    }

}

