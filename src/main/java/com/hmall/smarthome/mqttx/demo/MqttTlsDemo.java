package com.hmall.smarthome.mqttx.demo;


import com.hmall.smarthome.mqttx.client.IMqttClient;
import com.hmall.smarthome.mqttx.client.MqttClient;
import com.hmall.smarthome.mqttx.client.MqttClientOptions;
import lombok.extern.slf4j.Slf4j;

import static com.hmall.smarthome.mqttx.demo.MqttConstants.*;


@Slf4j
public class MqttTlsDemo {
    public static void main(String[] args) {
        IMqttClient mqttClient = new MqttClient(MqttClientOptions.builder()
            .host(HOST)
            .port(PORT)
            .accessKey(ACCESS_KEY)
            .accessCode(ACCESS_CODE)
            .instanceId(INSTANCE_ID)
            .trustAll(false)
            // 可替换成对应实例的证书，默认证书下载地址（https://support.huaweicloud.com/devg-iothub/iot_02_1004.html#section3）
            // 可在linux机器上按照下列方法将pem证书格式转成jks格式
            // pem -> der  openssl x509 -outform der -in cert.pem -out cert.der
            // der -> jks  keytool -import -keystore cert.jks -file cert.der
            .jksSourceRootPath("DigiCertGlobalRootCA.jks")
            .jksPassword(null)
            .build());
        mqttClient.setRawMessageListener(message -> {
            // handler subscribe msg
            log.info("begin to handler msg. topic = {}, payload = {}", message.getTopic(),
                new String(message.getPayload()));
        });
        mqttClient.connect();
        mqttClient.subscribeTopic(SUBSCRIBE_TOPIC, null);
    }
}
