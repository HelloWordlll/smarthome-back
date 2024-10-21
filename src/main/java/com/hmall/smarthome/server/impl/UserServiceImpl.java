package com.hmall.smarthome.server.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hmall.smarthome.common.ResponseCode;
import com.hmall.smarthome.exception.CustomException;
import com.hmall.smarthome.mapper.UserMapper;
import com.hmall.smarthome.entry.pojo.User;
import com.hmall.smarthome.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> queryAllUsers() {
        return userMapper.queryAllUsers();
    }

    @Override
    public void addUser(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",user.getUsername());
        if(userMapper.selectCount(wrapper) > 0){
            throw new CustomException(ResponseCode.USER_EXISTS);
        }
        userMapper.insert(user);
    }
    @Override
    public boolean login(String name, String pwd) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username",name).eq("password",pwd)) != null;
    }
}
