package com.lrm.service;

import com.lrm.dao.BlogRepository;
import com.lrm.dao.TemplateRepository;
import com.lrm.po.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-08-06
 */
@Service
public class BlogServiceImpl extends TemplateServiceImpl<Blog> implements BlogService {
    @Autowired
    BlogRepository blogRepository;


    @Override
    public TemplateRepository<Blog> getTemplateRepository() {
        return blogRepository;
    }
}
