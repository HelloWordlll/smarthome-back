package com.hmall.smarthome.entry.vo;


import com.google.gson.JsonObject;
import lombok.Data;

import java.util.Map;

@Data
public class SetVO {
    private String id;
    private Map<Object, Object> paras;
}
