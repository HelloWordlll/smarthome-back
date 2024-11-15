package com.hmall.smarthome.controller;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hmall.smarthome.common.BaseResponse;
import com.hmall.smarthome.entry.vo.*;
import com.hmall.smarthome.server.IotService;
import com.huaweicloud.sdk.iotda.v5.IoTDAClient;
import com.huaweicloud.sdk.iotda.v5.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/iot")
@RequiredArgsConstructor
public class IotController {

    private final IotService iotService;

    private final IoTDAClient client;

    @GetMapping("/list")
    public BaseResponse list(@RequestParam String room){
        log.info("room:{}",room);
        List<TopVO> list = iotService.getTop(room);
        log.info("list:{}",list);
        return BaseResponse.success(list);
    }

    @PostMapping("/set")
    public BaseResponse set(@RequestBody SetVO setVo){
        log.info("setVo:{}",setVo);
        Gson gson = new Gson();
        String para = gson.toJson(setVo.getParas());
        if(iotService.set(setVo.getId(),para)){
            return BaseResponse.success();
        }else {
            return BaseResponse.error("设置失败");
        }
    }
}
