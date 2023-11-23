package com.czx.mapper;
import com.czx.pojo.Menu;
import com.czx.pojo.Role;
import com.czx.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> getAllUsers();
    List<Menu> getMenuByUsername(String username);
    User getUserByUsername(String username);

    int insert(User user);

    int delete(long user_id);

    int update(User user);

    Role getRoleByUsername(String username);
    Role getRoleByUserid(long user_id);
}
