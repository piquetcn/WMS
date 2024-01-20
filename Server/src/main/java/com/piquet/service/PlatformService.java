package com.piquet.service;

import com.piquet.pojo.PageBean;
import com.piquet.pojo.Platform;

import java.util.List;

public interface PlatformService {
    void add(Platform platform);

    void delete(Integer id);

    void update(Platform platform);

    PageBean<Platform> list(Integer pageNum, Integer pageSize, String platformName);

    List<Platform> findAll();
}
