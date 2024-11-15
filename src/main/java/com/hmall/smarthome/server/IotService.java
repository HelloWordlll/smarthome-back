package com.hmall.smarthome.server;

import com.google.gson.JsonObject;
import com.hmall.smarthome.entry.vo.DeviceVO;
import com.hmall.smarthome.entry.vo.TopVO;

import java.util.List;

public interface IotService {

    List<TopVO> getTop(String room);

    List<DeviceVO> getList();

    String getToken();

    JsonObject getDeviceInfo(String deviceId);

    boolean updateDevice(String deviceId, String msg, String msgdata);

    boolean set(String id, String paras);
}
