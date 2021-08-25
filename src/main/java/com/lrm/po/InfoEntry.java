package com.lrm.po;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_infoEntry")
public class InfoEntry {
    /**
     * 主键
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 标题
     * 前端必填
     */
    @NotBlank(message = "请输入标题")
    private String title;

    /**
     * 懒加载
     * 内容
     * 前端必填
     */
    @Lob
    @NotBlank(message = "请输入概述")
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

    public List<User> getContributors() {
        return contributors;
    }

    public void setContributors(List<User> contributors) {
        this.contributors = contributors;
    }
}
