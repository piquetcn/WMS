package com.piquet.service.Impl;

import com.piquet.mapper.UserMapper;
import com.piquet.service.UserService;
import com.piquet.pojo.User;
import com.piquet.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        User user = userMapper.findByUsername(username);
        return user;
    }

    @Override
    public void register(String username, String password) {
        // 加密
        String md5String = Md5Util.getMD5String(password);
        // 添加
        userMapper.add(username, md5String);
    }

    @Override
    public List<User> nickNameList() {
        List<User> ul = userMapper.nicknameList();
        return ul;
    }
}
