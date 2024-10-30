package com.hmall.smarthome.server.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hmall.smarthome.config.IotConfig;
import com.hmall.smarthome.entry.vo.DeviceVO;
import com.hmall.smarthome.server.IotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class IotServiceImpl implements IotService {

    private final IotConfig iotconfig;
    private final Gson gson;

    @Override
    public List<DeviceVO> getList() {
        List<DeviceVO> deviceVOS = new ArrayList<>();

        OkHttpClient client = new OkHttpClient.Builder().build();

        String url = "https://" + iotconfig.getEndpoint() + "/v5/iot/" + iotconfig.getProjectid() + "/devices";
        log.info(url);

        boolean flag = true;
        while (true) {
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("X-Auth-Token", iotconfig.getToken()) // 确保添加 Authorization 头
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
                    JsonArray devicesArray = jsonObject.getAsJsonArray("devices");
                    log.info("设备列表：" + devicesArray);

                    // 将 JSON 数组转换为 Device 对象列表
                    for (int i = 0; i < devicesArray.size(); i++) {
                        DeviceVO device = gson.fromJson(devicesArray.get(i), DeviceVO.class);
                        deviceVOS.add(device);
                    }
                    break;
                } else if (response.code() == 401 && flag) {
                    log.info("token过期");
                    iotconfig.setToken(getToken()); // 更新 token
                    flag = false;
                } else {
                    String errorMessage = response.body() != null ? response.body().string() : "无返回信息";
                    log.info("请求失败，状态码：" + response.code() + "，错误信息：" + errorMessage);
                    break;
                }
            } catch (Exception e) {
                log.info(e.toString());
            }
        }
        return deviceVOS;
    }

    @Override
    public String getToken() {
        OkHttpClient client = new OkHttpClient();

        String url = "https://iam.myhuaweicloud.com/v3/auth/tokens";

        // 使用 JSONObject 构建 JSON
        JSONObject json = new JSONObject();
        JSONObject auth = new JSONObject();
        JSONObject identity = new JSONObject();
        JSONObject password = new JSONObject();
        JSONObject user = new JSONObject();
        JSONObject domain = new JSONObject();

        domain.put("name", "hid_sxao9viyeikm_qa");
        user.put("domain", domain);
        user.put("name", "ljk");
        user.put("password", "Ljk123456789..");
        password.put("user", user);
        identity.put("methods", new JSONArray().put("password"));
        identity.put("password", password);
        auth.put("identity", identity);
        auth.put("scope", new JSONObject().put("domain", domain));

        String jsonString = json.put("auth", auth).toString();

        RequestBody body = RequestBody.create(jsonString, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        String token = "";
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                token = response.headers().get("X-Subject-Token");
                log.info("Token: " + token);
            } else {
                String errorMessage = response.body() != null ? response.body().string() : "无返回信息";
                log.info("请求失败，状态码：" + response.code() + "，错误信息：" + errorMessage);
            }
        } catch (IOException e) {
            log.info("请求异常: " + e.toString());
        }

        return token;
    }
}
