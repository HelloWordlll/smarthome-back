package com.hmall.smarthome.entry.vo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class DeviceVO {
    @SerializedName("app_id")
    private String appId;

    @SerializedName("app_name")
    private String appName;

    @SerializedName("device_id")
    private String deviceId;

    @SerializedName("node_id")
    private String nodeId;

    @SerializedName("gateway_id")
    private String gatewayId;

    @SerializedName("device_name")
    private String deviceName;

    @SerializedName("node_type")
    private String nodeType;

    @SerializedName("description")
    private String description;

    @SerializedName("fw_version")
    private String fwVersion;

    @SerializedName("sw_version")
    private String swVersion;

    @SerializedName("device_sdk_version")
    private String deviceSdkVersion;

    @SerializedName("product_id")
    private String productId;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("status")
    private String status;

    @SerializedName("tags")
    private List<String> tags;

    // Getters and Setters
}
