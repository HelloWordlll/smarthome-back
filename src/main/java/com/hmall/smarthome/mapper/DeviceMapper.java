package com.hmall.smarthome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hmall.smarthome.entry.pojo.Device;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceMapper extends BaseMapper<Device> {
}
