package com.lrm.po;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 标签
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@Entity
@Table(name = "t_tag")
public class Tag {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 父级标签Id
     * 前端必填
     */
    @NotNull(message = "请输入父级标签Id")
    Long parentTagId0;

    /**
     * 标签名称
     * 前端必填
     */
    @NotBlank(message = "请输入标签名称")
    private String name;


    /**
     * 标签的子标签
     */
    @OneToMany(mappedBy = "parentTag", cascade = CascadeType.REMOVE)
    private List<Tag> sonTags = new ArrayList<>();
    /**
     * 标签的父标签
     */
    @JsonIgnore
    @ManyToOne
    private Tag parentTag;

    /**
     * 不用级联删除 这块需要返回错误页面 告知管理员标签下有博客的情况下不能删除标签
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private List<Question> questions = new ArrayList<>();

    /**
     * 同上
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentTagId0() {
        return parentTagId0;
    }

    public void setParentTagId0(Long parentTagId0) {
        this.parentTagId0 = parentTagId0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        };
        if (!(o instanceof Tag)) {
            return false;
        };
        Tag tag = (Tag) o;
        return this.id.equals(tag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getParentTagId0(), getName(), getSonTags(), getParentTag(), getQuestions(), blogs);
    }


    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public List<Tag> getSonTags() {
        return sonTags;
    }

    public void setSonTags(List<Tag> sonTags) {
        this.sonTags = sonTags;
    }

    public Tag getParentTag() {
        return parentTag;
    }

    public void setParentTag(Tag parentTag) {
        this.parentTag = parentTag;
    }
}
