package com.czx.service;

import com.czx.pojo.Permission;
import com.czx.pojo.Role;

import java.util.List;

public interface PermissionService {
    List<Permission> getAllPermissions();
    int add(long role_id, long permission_id);
    int deleteById(Long role_id);
    Permission getByPermissionname(String permission_name);
    List<Permission> getByUsername(String username);
    public int addPermission(Permission permission);
    int deleteByPermissionId(Long permission_id);
    int updatePermission(Permission permission);
}
