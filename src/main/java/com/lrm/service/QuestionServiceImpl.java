package com.lrm.service;

import com.lrm.dao.QuestionRepository;
import com.lrm.dao.TemplateRepository;
import com.lrm.po.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@Service
public class QuestionServiceImpl extends TemplateServiceImpl<Question> implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public TemplateRepository<Question> getTemplateRepository() {
        return questionRepository;
    }
}
