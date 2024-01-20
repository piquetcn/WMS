package com.piquet.controller;

import com.piquet.mapper.AbstractMapper;
import com.piquet.pojo.Abstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/abstract")
public class AbstractController {

    @Autowired
    private AbstractMapper abstractMapper;

    @GetMapping("/all")
    private List<Abstract> findAll() {
        List<Abstract> al = abstractMapper.findAll();
        return al;
    }
}
