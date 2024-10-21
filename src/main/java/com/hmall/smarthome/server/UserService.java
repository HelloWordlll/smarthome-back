package com.hmall.smarthome.server;


import com.hmall.smarthome.entry.pojo.User;

import java.util.List;

public interface UserService {

    public List<User> queryAllUsers();

    void addUser(User user);

    boolean login(String name, String pwd);
}
