package com.czx.service.impl;

import com.czx.mapper.RoleMapper;
import com.czx.pojo.Role;
import com.czx.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    @Override
    public Role getInfo(String username) {
        return roleMapper.selectByRolename(username);
    }

    @Override
    public int deleteById(Long role_id) {
        return roleMapper.deleteById(role_id);
    }

    @Override
    public int updateRole(Role role) {
        return roleMapper.updateRole(role);
    }

    @Override
    public int addRole(Role role) {
        return roleMapper.addRole(role);
    }
}
