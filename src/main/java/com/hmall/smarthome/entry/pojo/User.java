package com.hmall.smarthome.entry.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("\"public\".\"user\"")
public class User {

    private int id;

    private String username;

    private String password;
}
