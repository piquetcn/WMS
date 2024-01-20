package com.piquet.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.piquet.mapper.PlatformMapper;
import com.piquet.pojo.Platform;
import com.piquet.pojo.PageBean;
import com.piquet.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformServiceImpl implements PlatformService {
    
    @Autowired
    private PlatformMapper platformMapper;
    
    @Override
    public void add(Platform platform) {
        platformMapper.add(platform);
    }

    @Override
    public void delete(Integer id) {
        platformMapper.delete(id);
    }

    @Override
    public void update(Platform platform) {
        platformMapper.update(platform);
    }

    @Override
    public PageBean<Platform> list(Integer pageNum, Integer pageSize, String platformName) {
        PageBean<Platform> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<Platform> pl = platformMapper.list(platformName);
        Page<Platform> p = (Page<Platform>) pl;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());

        return pb;
    }

    @Override
    public List<Platform> findAll() {
        List<Platform> pl = platformMapper.findAll();
        return pl;
    }
}
