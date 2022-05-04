package com.lrm.po;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_infoEntry")
public class InfoEntry {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标题
     * 前端必填
     */
    @NotBlank(message = "请输入标题")
    private String title;

    /**
     * 别名
     */
//    @NotBlank(message = "请输入别名")
    private String alias;

    /**
     * 简介
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String discription;

    /**
     * 词条对应标签
     */
    @ManyToMany
    private List<EntryTag> entryTags = new ArrayList<>();
    /**
     * 懒加载
     * 内容
     * 前端必填
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String newContent;

    /**
     * 要显示的
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
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

    @ManyToMany
    private List<User> contributors = new ArrayList<>();

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

    public List<User> getContributors() {
        return contributors;
    }

    public void setContributors(List<User> contributors) {
        this.contributors = contributors;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public List<EntryTag> getEntryTags() {
        return entryTags;
    }

    public void setEntryTags(List<EntryTag> entryTags) {
        this.entryTags = entryTags;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
