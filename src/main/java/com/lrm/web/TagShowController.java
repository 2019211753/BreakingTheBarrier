package com.lrm.web;

import com.lrm.po.Tag;
import com.lrm.po.Template;
import com.lrm.service.BlogServiceImpl;
import com.lrm.service.QuestionServiceImpl;
import com.lrm.service.TagServiceImpl;
import com.lrm.service.TemplateServiceImpl;
import com.lrm.util.DataStructureUtils;
import com.lrm.vo.Magic;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 按标签查询博客
     *
     * @param tagIds 以,分割的标签Id
     * @return 所有标签及其子集下的所有问题分页
     */
    @PostMapping("/searchQuestions")
    public Result showQuestions(@RequestBody Map<String, String> tagIds) {
        return getTemplateByTagIds(tagIds.get("tagIds"), questionServiceImpl);
    }

    /**
     * 按标签问题
     *
     * @param tagIds 以,分割的标签Id
     * @return 所有标签及其子集下的所有问题分页
     */
    @PostMapping("/searchBlogs")
    public Result showBlogs(@RequestBody Map<String, String> tagIds) {
        return getTemplateByTagIds(tagIds.get("tagIds"), blogServiceImpl);
    }

    <T extends Template> Result getTemplateByTagIds(String tagIds, TemplateServiceImpl<T> templateServiceImpl) {
        Map<String, Object> hashMap = new HashMap<>(1);

        //需要查询的初始标签
        List<Tag> tags = tagServiceImpl.listTag(tagIds);

        //初始标签+子标签，按标签名排序
        Set<Tag> tagSet = new TreeSet<>(Comparator.comparing(Tag::getName));
        for (Tag tag : tags) {
            tagSet.addAll(tagServiceImpl.listTags(tag));
        }

        //将标签下的所有问题全塞进去 用set去重
        LinkedHashSet<T> ts = new LinkedHashSet<>();
        for (Tag tag : tagSet) {
            ts.addAll(templateServiceImpl.listByTagId(tag.getId()));
        }

        //每页十条
        Pageable pageRequest = PageRequest.of(1, Magic.PAGE_SIZE);

        //将set转换为Page
        hashMap.put("pages", DataStructureUtils.listConvertToPage(new ArrayList<>(ts), pageRequest));

        return new Result(hashMap, "搜索成功");
    }
}
