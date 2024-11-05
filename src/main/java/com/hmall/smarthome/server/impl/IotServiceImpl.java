package com.hmall.smarthome.server.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hmall.smarthome.config.IotConfig;
import com.hmall.smarthome.entry.pojo.Rooms;
import com.hmall.smarthome.entry.vo.DeviceVO;
import com.hmall.smarthome.entry.vo.TopVO;
import com.hmall.smarthome.interceptor.AuthInterceptor;
import com.hmall.smarthome.mapper.RoomsMapper;
import com.hmall.smarthome.server.IotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class IotServiceImpl implements IotService {

    private final IotConfig iotconfig;
    private final Gson gson;

    private final RoomsMapper roomsMapper;

    private OkHttpClient client;

    @Autowired
    public IotServiceImpl(IotConfig iotConfig, RoomsMapper roomsMapper, Gson gson) {
        this.iotconfig = iotConfig;
        this.roomsMapper = roomsMapper;
        this.gson = gson;

        // 初始化 OkHttpClient，并添加拦截器
        this.client = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(iotConfig, this))
                .build();
    }

    @Override
    public List<TopVO> getTop(String room) {
        QueryWrapper<Rooms> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_name",room);
        List<Rooms> rooms = roomsMapper.selectList(queryWrapper);

        if(!rooms.isEmpty()){
            List<TopVO> topVOS = new ArrayList<>();
            for(Rooms room1 : rooms){
                TopVO topVO = new TopVO();
                topVO.setDevid(room1.getDeviceId());
                topVO.setDevname(room1.getDeviceName());
                topVO.setType(room1.getType());
                JsonObject json = getDeviceInfo(room1.getDeviceId());
                JsonObject properties = json.getAsJsonObject("reported")
                        .getAsJsonObject("properties");

                if (properties != null) {
                    for (Map.Entry<String, JsonElement> entry : properties.entrySet()) {
                        topVO.setMsg(entry.getKey());           // 设置动态的键
                        topVO.setMsgdata(entry.getValue().getAsString());  // 设置对应的值
                    }
                }

                topVOS.add(topVO);
            }
            return topVOS;
        }
        return null;
    }

    @Override
    public List<DeviceVO> getList() {
        List<DeviceVO> deviceVOS = new ArrayList<>();

        String url = "https://" + iotconfig.getEndpoint() + "/v5/iot/" + iotconfig.getProjectid() + "/devices";
        log.info(url);

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

                for (int i = 0; i < devicesArray.size(); i++) {
                    DeviceVO device = gson.fromJson(devicesArray.get(i), DeviceVO.class);
                    deviceVOS.add(device);
                }
            } else  {
                String errorMessage = response.body() != null ? response.body().string() : "无返回信息";
                log.info("请求失败，状态码：" + response.code() + "，错误信息：" + errorMessage);
            }
        } catch (Exception e) {
            log.info(e.toString());
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

    @Override
    public JsonObject getDeviceInfo(String deviceId) {
        String url = "https://" + iotconfig.getEndpoint() + "/v5/iot/" + iotconfig.getProjectid() + "/devices/" + deviceId + "/shadow";
        log.info(url);

        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String jsonData = response.body().string();
                JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
                JsonArray devicesArray = jsonObject.getAsJsonArray("shadow");
                return devicesArray.get(0).getAsJsonObject();
            } else {
                String errorMessage = response.body() != null ? response.body().string() : "无返回信息";
                log.info("请求失败，状态码：" + response.code() + "，错误信息：" + errorMessage);
            }
        } catch (Exception e) {
            log.info(e.toString());
        }
        return null;
    }
    @Override
    public boolean updateDevice(String deviceId, String msg, String msgdata) {
        String url = "https://" + iotconfig.getEndpoint() + "/v5/iot/" + iotconfig.getProjectid() + "/devices/" + deviceId + "/commands";

        JsonObject jsonObject = new JsonObject();
// 添加 service_id 和 command_name
        jsonObject.addProperty("service_id", "server");
        jsonObject.addProperty("command_name", "SET");

// 创建 JsonArray 并添加 msg 和 msgdata
        JsonObject parasObject = new JsonObject();
        parasObject.addProperty(msg, msgdata); // 这里的 "开" 是你希望设置的值

// 将 paras JsonObject 添加到 jsonObject
        jsonObject.add("paras", parasObject);
        log.info("更新设备信息" + jsonObject.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(jsonObject.toString(), MediaType.get("application/json; charset=utf-8")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return true;
            } else {
                String errorMessage = response.body() != null ? response.body().string() : "无返回信息";
                log.info("请求失败，状态码：" + response.code() + "，错误信息：" + errorMessage);
            }
        } catch (Exception e) {
            log.info(e.toString());
        }
        return false;
    }
}