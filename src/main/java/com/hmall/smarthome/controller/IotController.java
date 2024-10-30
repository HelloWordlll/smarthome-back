package com.hmall.smarthome.controller;


import com.hmall.smarthome.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/iot")
public class IotController {

    @GetMapping("/list")
    public BaseResponse list(){

        return BaseResponse.success();
    }
}
