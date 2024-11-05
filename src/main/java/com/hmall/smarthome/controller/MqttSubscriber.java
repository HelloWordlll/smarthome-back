package com.hmall.smarthome.controller;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttSubscriber {
    public static void main(String[] args) {
        String brokerUrl = "ssl://2b37833f78.st1.iotda-app.cn-east-3.myhuaweicloud.com:8883"; // 替换为你的 MQTT Broker 地址和端口
        String clientId = "JavaMqttClient";
        String topic = "/rule"; // 替换为要订阅的主题
        String accessKey = "tme8yXw0"; // 替换为你的 access_key
        String accessCode = "TrU8QXUg1Og8MhOgVcUvA8ZjsibQgFC6"; // 替换为你的 access_code

        try {
            // 创建 MQTT 客户端
            MqttClient client = new MqttClient(brokerUrl, clientId);

            // 配置连接选项
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName("accessKey=" + accessKey);
            options.setPassword(accessCode.toCharArray());
            options.setCleanSession(true);

            // 设置回调函数
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection lost: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Received message from topic " + topic + ": " + new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // 这里是发布消息的回调，订阅模式下无需使用
                }
            });

            // 连接到 MQTT Broker
            client.connect(options);
            System.out.println("Connected to broker: " + brokerUrl);

            // 订阅主题
            client.subscribe(topic);
            System.out.println("Subscribed to topic: " + topic);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
