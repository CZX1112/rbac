package com.czx.mapper;

import com.czx.pojo.Permission;
import com.czx.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper {
    List<Permission> getAllPermissions();
    int add(long role_id, long permission_id);
    int deleteById(Long role_id);
    Permission getByPermissionname(String permission_name);
    List<Permission> getByUsername(String username);
    int addPermission(Permission permission);
    int deleteByPermissionId(Long permission_id);
    int updatePermission(Permission permission);
}
