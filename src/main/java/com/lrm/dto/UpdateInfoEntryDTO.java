package com.lrm.dto;

import com.lrm.po.EntryTag;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class UpdateInfoEntryDTO {
    private Long id;
    /**
     * 标题
     * 前端必填
     */
    private String title;

    /**
     * 别名
     */
    private String alias;

    /**
     * 简介
     */
    private String discription;

    /**
     * 词条对应标签
     */
    private String entryTags;
    /**
     * 懒加载
     * 内容
     * 前端必填
     */
    private String newContent;

    /**
     * 要显示的
     */
    private String currentContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getEntryTags() {
        return entryTags;
    }

    public void setEntryTags(String entryTags) {
        this.entryTags = entryTags;
    }

    public String getNewContent() {
        return newContent;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }

    public String getCurrentContent() {
        return currentContent;
    }

    public void setCurrentContent(String currentContent) {
        this.currentContent = currentContent;
    }
}
