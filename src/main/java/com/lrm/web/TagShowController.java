package com.lrm.web;

import com.lrm.service.BlogServiceImpl;
import com.lrm.service.QuestionServiceImpl;
import com.lrm.service.TagServiceImpl;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * 标签操作
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@RestController
@RequestMapping("/tags")
public class TagShowController {
    @Autowired
    private TagServiceImpl tagServiceImpl;

    @Autowired
    private QuestionServiceImpl questionServiceImpl;

    @Autowired
    private BlogServiceImpl blogServiceImpl;

    /**
     * @return 返回第一级标签 同时标签中含有它们子标签的属性 也就相当于返回标签树了
     */
    @GetMapping("/")
    public Result tags() {
        Map<String, Object> hashMap = new HashMap<>(1);

        hashMap.put("tags", tagServiceImpl.listTagTop());

        return new Result(hashMap, "");
    }
}
