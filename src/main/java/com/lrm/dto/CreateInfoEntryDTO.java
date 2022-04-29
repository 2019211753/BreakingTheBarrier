package com.lrm.dto;

import com.lrm.po.EntryTag;
import com.lrm.po.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CreateInfoEntryDTO {
    /**
     * 标题
     * 前端必填
     */
    private String title;

    /**
     * 别名
     */
//    @NotBlank(message = "请输入别名")
    private String alias;

    private String discription;

    public String getEntryTags() {
        return entryTags;
    }

    public void setEntryTags(String entryTags) {
        this.entryTags = entryTags;
    }

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

    /**
     * 暂时锁住
     */
    private boolean isLocked = false;

    /**
     * 管理员是否审批
     */
    private boolean isApproved = false;

    /**
     *
     */
    private Timestamp lastApprovedTime;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public Timestamp getLastApprovedTime() {
        return lastApprovedTime;
    }

    public void setLastApprovedTime(Timestamp lastApprovedTime) {
        this.lastApprovedTime = lastApprovedTime;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
