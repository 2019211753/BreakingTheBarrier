package com.lrm.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 博客
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-08-06
 */
@Entity
@Table(name = "t_blog")
public class Blog extends Template
{
    /**
     * 博客是否原创
     * 前端必填
     */
    @NotNull(message = "请选择是否原创")
    private Boolean origin;

    /**
     * 如果不是原创 在此注明作者
     */
    private String transferStatement;

    /**
     * 是否接受赞赏
     * 前端必填
     */
    @NotNull(message = "请选择是否接受赞赏")
    private Boolean appreciation;

    /**
     * 是否允许评论
     * 前端必填
     */
    @NotNull(message = "请选择是否允许评论")
    private Boolean commentAllowed;

    /**
     * 是否公开
     * 前端必填
     */
    @NotNull(message = "请选择是否公开发表")
    private Boolean open;

    /**
     * 博客更新时间
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Transient
    private String wechatPayCode;


    /**
     * 博客拥有的赞 不返回前端
     */
    @JsonIgnore
    @OneToMany(mappedBy = "blog", cascade = CascadeType.REMOVE)
    private List<Likes> likes = new ArrayList<>();;

    /**
     * 博客拥有的踩 不返回前端
     */
    @JsonIgnore
    @OneToMany(mappedBy = "blog", cascade = CascadeType.REMOVE)
    private List<DisLikes> dislikes = new ArrayList<>();;

    /**
     * 博客的子评论 不返回前端
     */
    @JsonIgnore
    @OneToMany(mappedBy = "blog", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();


    public Boolean getOrigin() {
        return origin;
    }

    public void setOrigin(Boolean origin) {
        this.origin = origin;
    }

    public String getTransferStatement() {
        return transferStatement;
    }

    public void setTransferStatement(String transferStatement) {
        this.transferStatement = transferStatement;
    }

    public Boolean getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(Boolean appreciation) {
        this.appreciation = appreciation;
    }

    public Boolean getCommentAllowed() {
        return commentAllowed;
    }

    public void setCommentAllowed(Boolean commentAllowed) {
        this.commentAllowed = commentAllowed;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getWechatPayCode() {
        return wechatPayCode;
    }

    public void setWechatPayCode(String wechatPayCode) {
        this.wechatPayCode = wechatPayCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Blog)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Blog blog = (Blog) o;
        return Objects.equals(getOrigin(), blog.getOrigin()) && Objects.equals(getTransferStatement(), blog.getTransferStatement()) && Objects.equals(getAppreciation(), blog.getAppreciation()) && Objects.equals(getCommentAllowed(), blog.getCommentAllowed()) && Objects.equals(getOpen(), blog.getOpen()) && Objects.equals(getUpdateTime(), blog.getUpdateTime()) && Objects.equals(getLikes(), blog.getLikes()) && Objects.equals(getDislikes(), blog.getDislikes()) && Objects.equals(getComments(), blog.getComments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getOrigin(), getTransferStatement(), getAppreciation(), getCommentAllowed(), getOpen(), getUpdateTime(), getLikes(), getDislikes(), getComments());
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Likes> getLikes() {
        return likes;
    }

    public void setLikes(List<Likes> likes) {
        this.likes = likes;
    }

    public List<DisLikes> getDislikes() {
        return dislikes;
    }

    public void setDislikes(List<DisLikes> dislikes) {
        this.dislikes = dislikes;
    }

}
