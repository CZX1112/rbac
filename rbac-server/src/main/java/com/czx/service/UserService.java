package com.czx.service;
import com.czx.pojo.Menu;
import com.czx.pojo.Role;
import com.czx.pojo.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();
    public List<Menu> getMenuByUsername(String username);
    public User getUserByUsername(String username);
    public int addUser(User user);

    public int deleteUser(long user_id);
    public int updateUser(User user);

    public Role getRoleByUsername(String username);
    public Role getRoleByUserid(long user_id);
    public User getUser(String username);
}
