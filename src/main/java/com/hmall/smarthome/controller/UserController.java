package com.hmall.smarthome.controller;


import com.hmall.smarthome.common.BaseResponse;
import com.hmall.smarthome.entry.pojo.User;
import com.hmall.smarthome.entry.vo.UserVo;
import com.hmall.smarthome.server.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> queryAl(){
        return  userService.queryAllUsers();
    }

    @PostMapping("/zhuche")
    public BaseResponse updateUser(@RequestBody UserVo uservo){
        System.out.println(uservo);
        User user = new User();
        user.setUsername(uservo.getUsername());
        user.setPassword(uservo.getPassword());
        userService.addUser(user);
        return BaseResponse.success();
    }

    @PostMapping("/login")
    public BaseResponse login(@RequestBody UserVo uservo){
        if(!userService.login(uservo.getUsername(), uservo.getPassword())){
            return BaseResponse.error("用户名或密码错误");
        }else {
            System.out.println("登录成功");
        }
        return BaseResponse.success();
    }
}
