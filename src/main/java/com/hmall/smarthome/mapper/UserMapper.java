package com.hmall.smarthome.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hmall.smarthome.entry.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    public List<User> queryAllUsers();

    public User queryUserById(int id);
}
