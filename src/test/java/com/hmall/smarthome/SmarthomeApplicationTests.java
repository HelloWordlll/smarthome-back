package com.hmall.smarthome;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hmall.smarthome.entry.pojo.Rules;
import com.hmall.smarthome.mapper.RulesMapper;
import com.hmall.smarthome.server.IotService;
import com.hmall.smarthome.server.RulesServer;
import com.hmall.smarthome.server.impl.IotServiceImpl;
import com.huaweicloud.sdk.iotda.v5.model.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SmarthomeApplication.class})
class SmarthomeApplicationTests {

    @Autowired
    private IotService iotService;

    @Autowired
    private RulesMapper rulesMapper;

    @Test
    void contextLoads() {
//        System.out.println("-------------------" + iotService.getDeviceInfo("proid_door_door"));
//        System.out.println(rulesMapper.selectObjs(
//                new QueryWrapper<Rules>()
//                        .eq("device_id", "proid_door_door")));
//        System.out.println("----------------");
//
//        System.out.println(rulesMapper.selectList(
//                new QueryWrapper<Rules>()
//                        .eq("device_id", "proid_door_door")));

        System.out.println(iotService.updateDevice("proid_door_door", "状态", "开"));

    }
}
