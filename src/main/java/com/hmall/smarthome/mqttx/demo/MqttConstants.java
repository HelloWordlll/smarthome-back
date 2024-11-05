package com.hmall.smarthome.mqttx.demo;

public interface MqttConstants {
    /**
     * MQTT接入域名
     *
     * <a href="https://support.huaweicloud.com/usermanual-iothub/iot_01_00113.html">中国站配置说明</a>
     * <a href="https://support.huaweicloud.com/intl/zh-cn/usermanual-iothub/iot_01_00113.html">国际站配置说明</a>
     */
    String HOST = "2b37833f78.st1.iotda-app.cn-east-3.myhuaweicloud.com";

    /**
     * MQTT接入端口
     *
     * <a href="https://support.huaweicloud.com/usermanual-iothub/iot_01_00113.html">中国站配置说明</a>
     * <a href="https://support.huaweicloud.com/intl/zh-cn/usermanual-iothub/iot_01_00113.html">国际站配置说明</a>
     */
    int PORT = 8883;

    /**
     * 接入凭证键值
     *
     * <a href="https://support.huaweicloud.com/usermanual-iothub/iot_01_00113.html">中国站配置说明</a>
     * <a href="https://support.huaweicloud.com/intl/zh-cn/usermanual-iothub/iot_01_00113.html">国际站配置说明</a>
     */
    String ACCESS_KEY = "tme8yXw0";

    /**
     * 接入凭证密钥
     *
     * <a href="https://support.huaweicloud.com/usermanual-iothub/iot_01_00113.html">中国站配置说明</a>
     * <a href="https://support.huaweicloud.com/intl/zh-cn/usermanual-iothub/iot_01_00113.html">国际站配置说明</a>
     */
    String ACCESS_CODE = "TrU8QXUg1Og8MhOgVcUvA8ZjsibQgFC6";

    /**
     * 非必填参数，当同一region购买多个标准版实例该参数必填
     *
     * <a href="https://support.huaweicloud.com/usermanual-iothub/iot_01_00113.html">中国站配置说明</a>
     * <a href="https://support.huaweicloud.com/intl/zh-cn/usermanual-iothub/iot_01_00113.html">国际站配置说明</a>
     */
    String INSTANCE_ID = "ab4dc2fc-84a1-4790-bf89-732c16598a6b";

    /**
     * 接收数据的Topic，替换成"创建规则动作"中的Topic
     */
    String SUBSCRIBE_TOPIC = "/rule";
}
