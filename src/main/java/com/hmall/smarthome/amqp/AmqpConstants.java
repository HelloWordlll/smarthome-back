package com.hmall.smarthome.amqp;


public interface AmqpConstants {
    /**
     * AMQP接入域名
     * 参考：https://support.huaweicloud.com/usermanual-iothub/iot_01_00100_2.html#section2
     */
    String HOST = "2b37833f78.st1.iotda-app.cn-east-3.myhuaweicloud.com";   // eg: "****.iot-amqps.cn-north-4.myhuaweicloud.com";

    /**
     * AMQP接入端口
     * 参考：https://support.huaweicloud.com/usermanual-iothub/iot_01_00100_2.html#section2
     */
    int PORT = 5671;

    /**
     * 接入凭证键值
     * 参考：https://support.huaweicloud.com/usermanual-iothub/iot_01_00100_2.html#section3
     */
    String ACCESS_KEY = "9K3UkfVM";

    /**
     * 接入凭证密钥
     * 参考：https://support.huaweicloud.com/usermanual-iothub/iot_01_00100_2.html#section3
     */
    String ACCESS_CODE = "uKOxfZn8uqJNKeoi2WyO5eJb5skaRYxI";

    /**
     * 默认队列
     */
    String DEFAULT_QUEUE = "smarthome_amqp";
}
