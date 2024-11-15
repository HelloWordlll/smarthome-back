package com.hmall.smarthome.entry.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("\"public\".\"rules\"")
public class Rules {

    @TableId(value = "rules_id", type = IdType.AUTO)
    @TableField("rules_id")
    private Long rulesId; // 使用 Long 类型来对应 "rules_id"
    @TableField("rule_name")
    private String ruleName; // 对应 "rule_name"
    @TableField("open")
    private Boolean open;
    @TableField("device_id")
    private String deviceId; // 对应 "device_id"
    @TableField("msg")
    private String msg; // 对应 "msg"
    @TableField("msgdata")
    private String msgdata; // 对应 "msgdata"
    @TableField("device_name")
    private String deviceName; // 对应 "msgdata"
}
