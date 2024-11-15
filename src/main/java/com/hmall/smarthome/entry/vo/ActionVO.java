package com.hmall.smarthome.entry.vo;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ActionVO {
    private String id;

    private String name;

    private String msg;

    private String msgdata;

    @JsonCreator
    public ActionVO(
            @JsonProperty("id") String deviceId,
            @JsonProperty("name") String deviceName,
            @JsonProperty("msg") String msg,
            @JsonProperty("msgdata") String msgdata) {
        this.id = deviceId;
        this.name = deviceName;
        this.msg = msg;
        this.msgdata = msgdata;
    }
}
