package com.hmall.smarthome.controller;


import com.hmall.smarthome.common.BaseResponse;
import com.hmall.smarthome.entry.pojo.Rules;
import com.hmall.smarthome.entry.vo.RuleVO;
import com.hmall.smarthome.entry.vo.RuleVO2;
import com.hmall.smarthome.entry.vo.RuleVO3;
import com.hmall.smarthome.entry.vo.TopVO;
import com.hmall.smarthome.server.RulesServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/rules")
@RequiredArgsConstructor
public class RulesController {
    private final RulesServer rulesServer;

    @GetMapping("/list")
    public BaseResponse getRules(){
        List<Rules> rules = rulesServer.getRules();
        List<RuleVO> ruleVOList = rules.stream()
                .map(rule -> new RuleVO(rule.getRulesId(), rule.getRuleName(), rule.getOpen()))
                .collect(Collectors.toList());
        log.info("ruleVOList:{}",ruleVOList);
        return BaseResponse.success(ruleVOList);
    }

    @GetMapping("/list2")
    public BaseResponse getRules2(@RequestParam("id")Integer id){
        RuleVO2 rule = rulesServer.getRules2(id);
        log.info("rule:{}",rule);
        return BaseResponse.success(rule);
    }

    @PostMapping("/updata")
    public BaseResponse updateRules(@RequestBody RuleVO3 rule){
        log.info("rule:{}",rule);
        rulesServer.updata(rule);
        return BaseResponse.success();
    }

    @GetMapping("/delect")
    public BaseResponse deleteDelect(@RequestParam("id")Long id){
        return rulesServer.deleteRules(id) ? BaseResponse.success() : BaseResponse.error("删除失败");
    }
}
