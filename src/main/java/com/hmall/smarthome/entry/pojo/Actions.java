package com.hmall.smarthome.entry.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("\"public\".\"actions\"")
public class Actions {
    @TableField("actions_id")
    private Long actionsId; // 使用 Long 类型来对应 "actions_id"
    @TableField("rule_id")
    private Long ruleId; // 对应 "rule_id"
    @TableField("device_id")
    private String deviceId; // 对应 "device_id"
    @TableField("msg")
    private String msg; // 对应 "msg"
    @TableField("msgdata")
    private String msgdata; // 对应 "msgdata"

    @TableField("device_name")
    private String deviceName; // 对应 "device_id"
}
