package com.hmall.smarthome.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hmall.smarthome.entry.pojo.Actions;
import com.hmall.smarthome.entry.pojo.Conditions;
import com.hmall.smarthome.entry.pojo.Rules;
import com.hmall.smarthome.mapper.ActionsMapper;
import com.hmall.smarthome.mapper.ConditionsMapper;
import com.hmall.smarthome.mapper.RulesMapper;
import com.hmall.smarthome.server.IotService;
import com.hmall.smarthome.server.RulesServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class RulesServerImpl implements RulesServer {
    private final RulesMapper rulesMapper;
    private final ConditionsMapper conditionsMapper;
    private final ActionsMapper actionsMapper;

    private final IotService iotService;

//    public boolean doRules(String msg){
//        JsonElement jsonElement = JsonParser.parseString(msg);
//        JsonObject jsonObject = jsonElement.getAsJsonObject();
//        JsonObject notify = jsonObject.getAsJsonObject("notify_data");
//        JsonObject header = notify.getAsJsonObject("header");
//        String deviceId = header.get("device_id").getAsString();
//        List<Rules> devices = rulesMapper.selectList(new QueryWrapper<Rules>().eq("open", true).eq("device_id", deviceId));
//        if (!devices.isEmpty()) {
//            JsonObject body = notify.getAsJsonObject("body");
//            JsonObject properties = body.getAsJsonObject("services").getAsJsonObject("properties");
//            if (properties != null) {
//                log.info("--------设备信息：" + properties);
//                for(Rules rules : devices){
//                    for (Map.Entry<String, JsonElement> entry : properties.entrySet()) {
//                        if (entry.getKey().equals(rules.getMsg())) {
//                            if (rules.getMsgdata().equals(entry.getValue().getAsString())) {
//                                List<Actions> actions = actionsMapper.selectList(new QueryWrapper<Actions>().eq("rule_id", rules.getRulesId()));
//                                for (Actions action : actions) {
//                                    iotService.updateDevice(action.getDeviceId(), action.getMsg(), action.getMsgdata());
//                                    log.info("--------设备控制：" + action.getDeviceId() + " " + action.getMsg() + " " + action.getMsgdata());
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            return true;
//        }else{
//            return false;
//        }
//    }
public boolean doRules(String msg) {
    JsonElement jsonElement = JsonParser.parseString(msg);
    JsonObject jsonObject = jsonElement.getAsJsonObject();

    // 提取 notify_data
    JsonObject notify = jsonObject.getAsJsonObject("notify_data");
    JsonObject header = notify.getAsJsonObject("header");
    String deviceId = header.get("device_id").getAsString();

    // 查询开启的规则，确保它返回的是 List<Rules>
    List<Rules> devices = rulesMapper.selectList(new QueryWrapper<Rules>().eq("open", true).eq("device_id", deviceId));
    if (!devices.isEmpty()) {
        JsonObject body = notify.getAsJsonObject("body");

        // 处理 services 数组
        JsonArray servicesArray = body.getAsJsonArray("services");
        if (servicesArray != null) {
            for (JsonElement serviceElement : servicesArray) {
                JsonObject service = serviceElement.getAsJsonObject();
                JsonObject properties = service.getAsJsonObject("properties");
                processProperties(properties, devices); // 处理 properties
            }
        }
        return true;
    } else {
        return false;
    }
}

    private void processProperties(JsonObject properties, List<Rules> devices) {
        if (properties != null) {
            log.info("--------设备信息：" + properties);
            for (Rules rules : devices) {
                for (Map.Entry<String, JsonElement> entry : properties.entrySet()) {
                    if (entry.getKey().equals(rules.getMsg())) {
                        if (rules.getMsgdata().equals(entry.getValue().getAsString())) {
                            List<Actions> actions = actionsMapper.selectList(new QueryWrapper<Actions>().eq("rule_id", rules.getRulesId()));
                            for (Actions action : actions) {
                                iotService.updateDevice(action.getDeviceId(), action.getMsg(), action.getMsgdata());
                                log.info("--------设备控制：" + action.getDeviceId() + " " + action.getMsg() + " " + action.getMsgdata());
                            }
                        }
                    }
                }
            }
        }
    }




    public List<Rules> getRules() {
        return rulesMapper.selectList(null);
    }

    public boolean addRules(Rules rules) {
        return rulesMapper.insert(rules) > 0;
    }

    public boolean deleteRules(Integer id) {
        return rulesMapper.deleteById(id) > 0;
    }

    public boolean updateRules(Rules rules) {
        return rulesMapper.updateById(rules) > 0;
    }

    public Rules getRulesById(Integer id) {
        return rulesMapper.selectById(id);
    }

//    @Scheduled(fixedRate = 1000 * 10) // 每5秒执行一次
//    public void scheduledTask() {
////        log.info("定时任务执行中...");
//        QueryWrapper<Rules> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("open", true);
//        List<Rules> rules = rulesMapper.selectList(queryWrapper);
//
//        for (Rules rule : rules) {
//            QueryWrapper<Conditions> qw2 = new QueryWrapper<>();
//            qw2.eq("rule_id", rule.getRulesId());
//            List<Conditions> conditions = conditionsMapper.selectList(qw2);
//            for (Conditions condition : conditions) {
//                if (ifOK(condition)) {
////                    iotService.updateDevice(condition.getDeviceId(), condition.getMsg(), condition.getMsgdata());
//                    doAction(condition);
//                }
//            }
//        }
//    }

    public boolean ifOK(Conditions conditions) {
        JsonObject deviceInfo = iotService.getDeviceInfo(conditions.getDeviceId());
        if (deviceInfo != null) {
            JsonObject properties = deviceInfo.getAsJsonObject("reported")
                    .getAsJsonObject("properties");
            if (properties != null) {
                log.info("--------设备信息：" + properties);
                for (Map.Entry<String, JsonElement> entry : properties.entrySet()) {
                    if (entry.getKey().equals(conditions.getMsg())) {
                        if (conditions.getMsgdata().equals(entry.getValue().getAsString())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void doAction(Conditions conditions) {
        List<Actions> actions = actionsMapper.selectList(new QueryWrapper<Actions>().eq("rule_id", conditions.getRuleId()));
        for (Actions action : actions) {
            iotService.updateDevice(action.getDeviceId(), action.getMsg(), action.getMsgdata());
        }
    }
}
