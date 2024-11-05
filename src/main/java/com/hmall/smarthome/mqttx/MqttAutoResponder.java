package com.hmall.smarthome.mqttx;

import com.google.gson.JsonObject;
import com.hmall.smarthome.entry.pojo.Device;
import com.hmall.smarthome.mapper.DeviceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MqttAutoResponder {
    private static final String BROKER = "ssl://2b37833f78.st1.iotda-device.cn-east-3.myhuaweicloud.com:8883";

    private final DeviceMapper deviceMapper;

    private ExecutorService executorService;

    @PostConstruct
    public void init() {
        List<Device> devices = deviceMapper.selectList(null);

        executorService = Executors.newFixedThreadPool(devices.size());
        try {
            for (Device device : devices) {
                executorService.submit(() -> {
                    try {
                        connectAndSubscribe(device);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connectAndSubscribe(Device device) throws MqttException {
        MqttClient client = new MqttClient(BROKER, device.getClientid(), new MemoryPersistence());

        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        connOpts.setUserName(device.getUsername());
        connOpts.setPassword(device.getPassword().toCharArray());

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                log.info("Connection lost for device " + device.getClientid() + ", attempting to reconnect...");

                int i = 5;
                // 使用重试机制
                while (i-- > 0) {
                    try {
                        // 等待一段时间再尝试重连，避免频繁连接请求
                        Thread.sleep(5000); // 5秒后重试，可根据需要调整等待时间

                        client.reconnect(); // 尝试重新连接
                        log.info("Reconnected successfully for device " + device.getClientid());
                        break; // 重连成功，跳出循环

                    } catch (MqttException e) {
                        log.error("Reconnection attempt failed for device " + device.getClientid(), e);
                        // 连接失败后继续循环重试
                    } catch (InterruptedException e) {
                        log.error("Reconnection interrupted", e);
                        Thread.currentThread().interrupt(); // 恢复中断状态
                        break;
                    }
                }
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                log.info("Received message on topic: " + topic);

                // 使用字符串分割解析 device_id 和 request_id
                String[] topicLevels = topic.split("/");
                String deviceId = topicLevels[2]; // 提取 device_id
                String requestId = topic.split("request_id=")[1]; // 提取 request_id

                log.info("Device ID: " + deviceId);
                log.info("Request ID: " + requestId);

                // 解析消息并处理
                JSONObject command = new JSONObject(new String(message.getPayload()));
                String commandName = command.getString("command_name");

                if ("SET".equals(commandName)) {
                    JSONObject response = new JSONObject();
                    response.put("result_code", 0);
                    response.put("response_name", "COMMAND_RESPONSE");
                    response.put("paras", new JSONObject().put("result", "success"));

                    // 构建响应主题，包括动态的 device_id 和 request_id
                    String responseTopic = "$oc/devices/" + deviceId + "/sys/commands/response/request_id=" + requestId;
                    MqttMessage responseMessage = new MqttMessage(response.toString().getBytes());
                    client.publish(responseTopic, responseMessage);

                    log.info("Sent response to device " + deviceId + " with request ID " + requestId + ": " + response.toString());

                    JSONObject property = new JSONObject();
                    JSONObject paras = command.getJSONObject("paras");
                    for (String key : paras.keySet()) {
                        // 获取对应的值
                        property.put(key, paras.get(key));
                    }

                    JSONObject service = new JSONObject();
                    service.put("service_id", "server");
                    service.put("properties", property);
                    service.put("event_time", "20151212T121212Z"); // 示例事件时间，可以根据需要修改

                    // 创建包含服务信息的数组
                    JSONArray servicesArray = new JSONArray();
                    servicesArray.put(service);

                    JSONObject latestData = new JSONObject();
                    latestData.put("services", servicesArray);

                    // 构建推送数据的主题
                    String dataTopic = "$oc/devices/" + deviceId + "/sys/properties/report"; // 根据 IoT 平台要求设置主题
                    MqttMessage dataMessage = new MqttMessage(latestData.toString().getBytes());
                    dataMessage.setQos(1); // 设置 QoS，具体取值可以根据需求调整

                    client.publish(dataTopic, dataMessage);
                    log.info("Published latest data for device " + deviceId + ": " + latestData.toString());
                }  else {
                    // 不是 "SET" 命令的处理
                    JSONObject errorResponse = new JSONObject();
                    errorResponse.put("result_code", 1); // 自定义错误码，例如 1 表示未知命令
                    errorResponse.put("response_name", "COMMAND_ERROR");
                    errorResponse.put("error_message", "Unsupported command: " + commandName);

                    // 构建错误响应主题
                    String errorResponseTopic = "$oc/devices/" + deviceId + "/sys/commands/response/request_id=" + requestId;
                    MqttMessage errorResponseMessage = new MqttMessage(errorResponse.toString().getBytes());
                    client.publish(errorResponseTopic, errorResponseMessage);

                    log.info("Sent error response to device " + deviceId + " with request ID " + requestId + ": " + errorResponse.toString());
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                log.info("Delivery complete for device " + device.getClientid());
            }
        });

        client.connect(connOpts);
        String commandTopic = "$oc/devices/" + device.getDeviceId() + "/sys/properties/set/request_id";
        client.subscribe(commandTopic);

        log.info("Listening for commands for device " + device.getClientid() + "...");
    }

    @PreDestroy
    public void cleanup() {
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
