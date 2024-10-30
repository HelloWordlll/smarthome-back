package com.hmall.smarthome.server;

import com.hmall.smarthome.entry.vo.DeviceVO;

import java.util.List;

public interface IotService {

    List<DeviceVO> getList();

    String getToken();
}
