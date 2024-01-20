package com.piquet.service;

import com.piquet.pojo.User;

import java.util.List;

public interface UserService {

    User findByUserName(String username);

    void register(String username, String password);

    List<User> nickNameList();
}
