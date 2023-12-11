package com.piquet.service;

import com.piquet.pojo.User;

public interface UserService {

    User findByUserName(String username);

    void register(String username, String password);
}
