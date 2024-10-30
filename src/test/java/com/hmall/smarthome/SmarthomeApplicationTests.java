package com.hmall.smarthome;

import com.hmall.smarthome.server.IotService;
import com.hmall.smarthome.server.impl.IotServiceImpl;
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

    @Test
    void contextLoads() {
        System.out.println(iotService.getList());
    }
}
