package com.czx.service.impl;

import com.czx.mapper.UserMapper;
import com.czx.pojo.Menu;
import com.czx.pojo.Role;
import com.czx.pojo.User;
import com.czx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Override
    public List<Menu> getMenuByUsername(String username) {
        return userMapper.getMenuByUsername(username);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public int addUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int deleteUser(long user_id) {
        return userMapper.delete(user_id);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.update(user);
    }

    @Override
    public Role getRoleByUsername(String username) {
        return userMapper.getRoleByUsername(username);
    }

    @Override
    public Role getRoleByUserid(long user_id) {
        return userMapper.getRoleByUserid(user_id);
    }

    @Override
    public User getUser(String username) {
        return userMapper.getUserByUsername(username);
    }
}
