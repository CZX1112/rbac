package com.czx.service.impl;

import com.czx.mapper.RoleMapper;
import com.czx.mapper.UserRoleMapper;
import com.czx.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public int add(long user_id, long role_id) {
        return userRoleMapper.add(user_id, role_id);
    }

    @Override
    public int update(long user_id, long role_id) {
        return userRoleMapper.update(user_id, role_id);
    }
}
