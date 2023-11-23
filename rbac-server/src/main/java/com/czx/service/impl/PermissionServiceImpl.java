package com.czx.service.impl;

import com.czx.mapper.PermissionMapper;
import com.czx.pojo.Permission;
import com.czx.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public List<Permission> getAllPermissions() {
        return permissionMapper.getAllPermissions();
    }

    @Override
    public int add(long role_id, long permission_id) {
        return permissionMapper.add(role_id, permission_id);
    }

    @Override
    public int deleteById(Long role_id) {
        return permissionMapper.deleteById(role_id);
    }

    @Override
    public Permission getByPermissionname(String permission_name) {
        return permissionMapper.getByPermissionname(permission_name);
    }

    @Override
    public List<Permission> getByUsername(String username) {
        return permissionMapper.getByUsername(username);
    }

    @Override
    public int addPermission(Permission permission) {
        return permissionMapper.addPermission(permission);
    }

    @Override
    public int deleteByPermissionId(Long permission_id) {
        return permissionMapper.deleteByPermissionId(permission_id);
    }

    @Override
    public int updatePermission(Permission permission) {
        return permissionMapper.updatePermission(permission);
    }
}
