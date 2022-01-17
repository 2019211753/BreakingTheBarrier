package com.lrm.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 收藏夹
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@Entity
@Table(name = "t_favorite")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 收藏夹的名字
     * 前端必填
     */
    @NotBlank(message = "请输入收藏夹标题")
    private String title;

    /**
     * 是否公开 true为公开
     * 前端必填
     */
    @NotNull(message = "请选择是否公开")
    private Boolean open;

    /**
     * 创建收藏夹的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 上一次添加收藏的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 收藏几个了
     */
    private int size;

    /**
     * 收藏夹的主人
     */
    @JsonIgnore
    @ManyToOne
    private User owner;

    /**
     * 收藏夹里的问题
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "favorites")
    private List<Question> favoriteQuestions;

    /**
     * 收藏夹里的博客
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "favorites")
    private List<Blog> favoriteBlogs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Question> getFavoriteQuestions() {
        return favoriteQuestions;
    }

    public void setFavoriteQuestions(List<Question> favoriteQuestions) {
        this.favoriteQuestions = favoriteQuestions;
    }

    public List<Blog> getFavoriteBlogs() {
        return favoriteBlogs;
    }

    public void setFavoriteBlogs(List<Blog> favoriteBlogs) {
        this.favoriteBlogs = favoriteBlogs;
    }
}
