package com.lrm.service;

import com.lrm.dao.PosterRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PosterServiceImpl implements PosterService{
    @Autowired
    private PosterRepository posterRepository;

    public void test() {
        posterRepository.findAll();
    }
}
