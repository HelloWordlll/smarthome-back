package com.hmall.smarthome.entry.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("\"public\".\"rooms\"")
public class Rooms {
    @TableId
    private int roomId;
    private String roomName;
    private String deviceId;
    private String deviceName;
    private String productsId;

    @TableField("type")
    private String type;
}
