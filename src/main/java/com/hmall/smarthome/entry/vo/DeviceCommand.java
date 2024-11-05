package com.hmall.smarthome.entry.vo;

import lombok.Data;

@Data
public class DeviceCommand {
    private String commandName;
    private String commandBody;
    private String serviceId;
    private Integer bufferTimeout;
    private String mode;
}
