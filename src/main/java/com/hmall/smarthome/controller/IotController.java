package com.hmall.smarthome.controller;


import com.hmall.smarthome.common.BaseResponse;
import com.hmall.smarthome.entry.vo.DeviceVO;
import com.hmall.smarthome.server.IotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/iot")
@RequiredArgsConstructor
public class IotController {

    private final IotService iotService;

    @GetMapping("/list")
    public BaseResponse list(@RequestBody String room){
        List<DeviceVO> list = iotService.getList();
        return BaseResponse.success(list);
    }
}
