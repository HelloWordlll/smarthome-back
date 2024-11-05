package com.hmall.smarthome.entry.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("\"public\".\"device\"")
public class Device {

    @TableField("dervice_id") // 使用 @TableField 指定数据库中的字段名
    private String deviceId;

    @TableField("dervice_name")
    private String deviceName;

    @TableField("clientid")
    private String clientid;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("proid")
    private String proid;


}
