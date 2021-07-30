package com.lrm.web;

import com.lrm.po.Question;
import com.lrm.po.Tag;
import com.lrm.service.QuestionService;
import com.lrm.service.TagService;
import com.lrm.util.ProcessData;
import com.lrm.vo.Magic;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


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
    private TagService tagService;

    @Autowired
    private QuestionService questionService;

    /**
     * @return 返回第一级标签 同时标签中含有它们子标签的属性 也就相当于返回标签树了
     */
    @GetMapping("/")
    public Result tags() {
        Map<String, Object> hashMap = new HashMap<>(1);

        hashMap.put("tags", tagService.listTagTop());

        return new Result(hashMap, "");
    }

    /**
     * 按标签查询
     *
     * @param tagIds 以,分割的标签Id
     * @return 所有标签及其子集下的所有问题分页
     */
    @PostMapping("/searchQuestions")
    public Result showQuestions(String tagIds) {
        Map<String, Object> hashMap = new HashMap<>(1);

        //需要查询的初始标签
        List<Tag> tags = tagService.listTag(tagIds);

        //初始标签+子标签，按标签名排序
        Set<Tag> tagSet = new TreeSet<>(Comparator.comparing(Tag::getName));
        for (Tag tag : tags) {
            tagSet.addAll(tagService.listTags(tag));
        }

        //将标签下的所有问题全塞进去 用set去重
        Set<Question> questions = new LinkedHashSet<>();
        for (Tag tag : tagSet) {
            questions.addAll(questionService.listQuestion(tag.getId()));
        }

        //每页十条
        Pageable pageRequest = PageRequest.of(1, Magic.PAGE_SIZE);

        //将set转换为Page
        hashMap.put("pages", ProcessData.listConvertToPage(ProcessData.setConvertToList(questions), pageRequest));

        return new Result(hashMap, "搜索成功");
    }
}
