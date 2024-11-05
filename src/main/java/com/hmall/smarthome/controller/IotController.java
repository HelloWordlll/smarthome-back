package com.hmall.smarthome.controller;


import com.hmall.smarthome.common.BaseResponse;
import com.hmall.smarthome.entry.vo.DeviceCommand;
import com.hmall.smarthome.entry.vo.DeviceVO;
import com.hmall.smarthome.entry.vo.RuleRequest;
import com.hmall.smarthome.entry.vo.TopVO;
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

//    @PostMapping("/rule")
//    public String createRule(@RequestBody RuleRequest ruleRequest) {
//        CreateRuleRequest request = new CreateRuleRequest();
//        Rule body = new Rule();
//
//        // 设置规则详细信息
//        body.withName(ruleRequest.getName())
//                .withDescription(ruleRequest.getDescription())
//                .withStatus("active")
//                .withRuleType("DEVICE_LINKAGE");
//
//        // 设置执行动作（设备命令）
//        List<RuleAction> actions = new ArrayList<>();
//        for (DeviceCommand deviceCommand : ruleRequest.getCommands()) {
//            Cmd cmdDeviceCommand = new Cmd()
//                    .withCommandName(deviceCommand.getCommandName())
//                    .withCommandBody(deviceCommand.getCommandBody())
//                    .withServiceId(deviceCommand.getServiceId())
//                    .withBufferTimeout(deviceCommand.getBufferTimeout())
//                    .withMode(deviceCommand.getMode());
//
//            ActionDeviceCommand deviceCommandActions = new ActionDeviceCommand()
//                    .withDeviceId(ruleRequest.getDeviceId())  // 使用传递的设备 ID
//                    .withCmd(cmdDeviceCommand);
//
//            actions.add(new RuleAction().withType("DEVICE_CMD").withDeviceCommand(deviceCommandActions));
//        }
//
//        body.withActions(actions);
//
//        // 设置条件（设备属性条件）
//        DeviceDataCondition deviceDataCondition = new DeviceDataCondition()
//                .withDeviceId(ruleRequest.getDeviceId())
//                .withFilters(ruleRequest.getFilters());
//
//        RuleCondition condition = new RuleCondition()
//                .withType("DEVICE_DATA")
//                .withDevicePropertyCondition(deviceDataCondition);
//
//        List<RuleCondition> conditions = new ArrayList<>();
//        conditions.add(condition);
//
//        ConditionGroup conditionGroup = new ConditionGroup()
//                .withConditions(conditions)
//                .withLogic("and");
//
//        body.withConditionGroup(conditionGroup);
//
//        request.withBody(body);
//
//        // 创建规则
//        try {
//            CreateRuleResponse response = client.createRule(request);
//            return "Rule created successfully: " + response.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error creating rule: " + e.getMessage();
//        }
//    }

    @GetMapping("/list")
    public BaseResponse list(@RequestParam String room){
        log.info("room:{}",room);
        List<TopVO> list = iotService.getTop(room);
        log.info("list:{}",list);
        return BaseResponse.success(list);
    }
}
