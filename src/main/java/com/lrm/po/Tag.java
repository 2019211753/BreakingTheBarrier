package com.lrm.po;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
     * 父级评论Id
     * 避免json序列无限递归 只好出此下策 真拙劣啊...
     */
    Long parentTagId0;

    /**
     * 标签名称 前端必填
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
    @ManyToOne
    private Tag parentTag;

    /**
     * 不用级联删除 这块需要返回错误页面 告知管理员标签下有博客的情况下不能删除标签
     */
    @ManyToMany(mappedBy = "tags")
    private List<Question> questions = new ArrayList<>();


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
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        Tag tag = (Tag) o;
        return getId().equals(tag.getId()) && getName().equals(tag.getName()) && getSonTags().equals(tag.getSonTags()) && getParentTag().equals(tag.getParentTag()) && getQuestions().equals(tag.getQuestions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSonTags(), getQuestions());
    }


    /**
     * @return 返回前端的标签 不 包含它下面的问题
     */
    @JsonBackReference
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    /**
     * @return 返回前端的标签包含它下面的子标签
     */
    @JsonManagedReference
    public List<Tag> getSonTags() {
        return sonTags;
    }

    public void setSonTags(List<Tag> sonTags) {
        this.sonTags = sonTags;
    }

    /**
     * @return 返回前端的标签 不 包含它的父标签
     */
    @JsonBackReference
    public Tag getParentTag() {
        return parentTag;
    }

    public void setParentTag(Tag parentTag) {
        this.parentTag = parentTag;
    }
}
