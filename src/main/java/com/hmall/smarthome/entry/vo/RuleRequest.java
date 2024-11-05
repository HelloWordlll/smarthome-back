package com.hmall.smarthome.entry.vo;

import com.huaweicloud.sdk.iotda.v5.model.DeviceCommandRequest;
import com.huaweicloud.sdk.iotda.v5.model.PropertyFilter;
import lombok.Data;

import java.util.List;

@Data
public class RuleRequest {

    private String name;
    private String description;
    private String deviceId;
    private List<PropertyFilter> filters;
    private List<DeviceCommand> commands;  // 新增的设备命令字段
}
