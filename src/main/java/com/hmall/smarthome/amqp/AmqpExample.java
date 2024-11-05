package com.hmall.smarthome.amqp;

import com.hmall.smarthome.amqp.AmqpClient;
import com.hmall.smarthome.amqp.AmqpConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmqpExample {
    private static final Logger log = LoggerFactory.getLogger(AmqpExample.class);

    public static void main(String[] args) {
        // 创建 AmqpClientOptions
        AmqpClientOptions options = AmqpClientOptions.builder()
                .host(AmqpConstants.HOST)
                .port(AmqpConstants.PORT)
                .accessKey(AmqpConstants.ACCESS_KEY)
                .accessCode(AmqpConstants.ACCESS_CODE)
//                .clientId("myClientId") // 根据实际情况设置
                .build();

        // 创建 AmqpClient
        AmqpClient amqpClient = new AmqpClient(options);

        try {
            // 初始化连接
            amqpClient.initialize();
            log.info("------------------------------连接成功，连接ID: " + amqpClient.getId());

            // 创建消息消费者
            amqpClient.newConsumer(AmqpConstants.DEFAULT_QUEUE);

            // 在这里可以开始处理消息
            System.out.println("连接成功，已创建消息消费者。");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            amqpClient.close();
        }
    }
}
