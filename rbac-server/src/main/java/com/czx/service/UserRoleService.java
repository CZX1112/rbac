package com.czx.service;

import com.czx.pojo.User;

public interface UserRoleService {
    public int add(long user_id, long role_id);
    public int update(long user_id, long role_id);
}
