package com.lrm.service;

import com.lrm.dao.BlogRepository;
import com.lrm.dao.TemplateRepository;
import com.lrm.po.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Predicate> setMoreCriteria(CriteriaBuilder cb, Root<Blog> root) {
        List<Predicate> listAnd = new ArrayList<>(1);
        listAnd.add(cb.equal(root.get("open"), true));
        return listAnd;
    }
}
