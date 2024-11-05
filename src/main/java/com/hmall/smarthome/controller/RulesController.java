package com.hmall.smarthome.controller;


import com.hmall.smarthome.common.BaseResponse;
import com.hmall.smarthome.entry.vo.TopVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@Slf4j
@Controller("/rules")
public class RulesController {

    @GetMapping("/list")
    public String getRules(){
        return "rules";
    }

    @GetMapping("/add")
    public String addRules(){
        return "addRules";
    }

    @GetMapping("/edit")
    public String editRules(){
        return "editRules";
    }

    @GetMapping("/detail")
    public String detailRules(){
        return "detailRules";
    }

    @PostMapping("/put")
    public BaseResponse putRules(){
        return null;
    }
}
