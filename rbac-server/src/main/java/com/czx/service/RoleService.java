package com.czx.service;

import com.czx.pojo.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();
    Role getInfo(String username);
    int deleteById(Long role_id);
    int updateRole(Role role);
    int addRole(Role role);
}
