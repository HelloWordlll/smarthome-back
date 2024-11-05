package com.hmall.smarthome.mqttx.demo;

import com.hmall.smarthome.mqttx.client.IMqttClient;
import com.hmall.smarthome.mqttx.client.MqttClient;
import com.hmall.smarthome.mqttx.client.MqttClientOptions;
import com.hmall.smarthome.server.RulesServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.hmall.smarthome.mqttx.demo.MqttConstants.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class MqttDemo {

    private final RulesServer rulesServer;

    @PostConstruct
    public void init() {
        mqtt();
    }

    public void mqtt() {
        IMqttClient mqttClient = new MqttClient(MqttClientOptions.builder()
                .host(HOST)
                .port(PORT)
                .accessKey(ACCESS_KEY)
                .accessCode(ACCESS_CODE)
                .instanceId(INSTANCE_ID)
                .build());
        mqttClient.setRawMessageListener(message -> {
            // 处理订阅消息
            log.info("begin to handler msg. topic = {}, payload = {}", message.getTopic(),
                    new String(message.getPayload()));
            rulesServer.doRules(new String(message.getPayload()));
        });
        mqttClient.connect();
        mqttClient.subscribeTopic(SUBSCRIBE_TOPIC, null);
    }
}
