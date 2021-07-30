package com.lrm.vo;


/**
 * 问题搜索包装类
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public class QuestionQuery
{
    private String title;

    private String tagIds;

    public QuestionQuery() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagIds()
    {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }
}
