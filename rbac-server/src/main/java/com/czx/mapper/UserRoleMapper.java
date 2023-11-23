package com.czx.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper {
    int add(long user_id, long role_id);
    int update(long user_id, long role_id);
}
