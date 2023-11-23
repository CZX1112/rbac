//package com.czx.mapper;
//import com.czx.pojo.Role;
//import org.apache.ibatis.annotations.*;
//
//import java.util.List;
//
//@Mapper
//public interface RoleMapper {
//
//    List<Role> getAllRoles();
//
//    int insert(Role role);
//
//    int delete(long role_id);
//
//    int update(Role role);
//}

package com.czx.mapper;

import com.czx.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role> getAllRoles();

    Role selectByRolename(String username);

    int deleteById(Long role_id);

    int updateRole(Role role);

    int addRole(Role role);
}
