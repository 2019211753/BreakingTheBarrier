package com.lrm.po;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
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
     * 创建收藏夹的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 上一次添加收藏的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 收藏夹的名字 前端必填
     */
    @NotBlank(message = "请输入收藏夹标题")
    private String title;

    /**
     * 收藏几个了
     */
    private int size;

    /**
     * 是否公开 true为公开
     */
    private Boolean open;

    /**
     * 收藏夹的上限容量
     */
    public static final int MAXSIZE = 25;


    /**
     * 收藏夹的主人
     */
    @ManyToOne
    private User owner;

    /**
     * 收藏夹里的问题
     */
    @ManyToMany(mappedBy = "favorites")
    private List<Question> favoriteQuestions = new ArrayList<>();


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

    public static int getMAXSIZE() {
        return MAXSIZE;
    }


    /**
     * @return 返回前端 不 包含收藏夹的主人
     */
    @JsonBackReference
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * @return 返回前端 不 包含收藏夹里的内容
     */
    @JsonBackReference
    public List<Question> getFavoriteQuestions() {
        return favoriteQuestions;
    }

    public void setFavoriteQuestions(List<Question> favoriteQuestions) {
        this.favoriteQuestions = favoriteQuestions;
    }
}
