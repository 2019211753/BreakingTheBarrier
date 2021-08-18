package com.lrm.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * 点赞
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@Entity
@Table(name = "t_likes")
public class Likes extends Emotion{
    /**
     * 是否已读
     * 不能用isRead 也不能用read关键字 只能这样了。。。
     */
    private Boolean looked;

    /**
     * 返回前端的点赞发布者Id
     */
    private Long postUserId0;

    /**
     * 返回前端的点赞所在问题Id
     */
    private Long questionId0;

    /**
     * 返回前端的点赞所在问题Id
     */
    private Long blogId0;

    /**
     * 封装成完整的"yyyy-MM-dd HH:mm:ss"的Date类型
     * 点赞时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;


    /**
     * 节约空间不入库
     * 返回前端的评论发布者的头像
     */
    @Transient
    private String avatar;

    /**
     * 节约空间不入库
     * 返回前端的评论发布者的昵称
     */
    @Transient
    private String nickname;


    /**
     * 赞的谁
     */
    @JsonIgnore
    @ManyToOne
    private User receiveUser;


    public Boolean getLooked() {
        return looked;
    }

    public void setLooked(Boolean looked) {
        this.looked = looked;
    }

    public Long getPostUserId0() {
        return postUserId0;
    }

    public void setPostUserId0(Long postUserId0) {
        this.postUserId0 = postUserId0;
    }

    public Long getQuestionId0() {
        return questionId0;
    }

    public void setQuestionId0(Long questionId0) {
        this.questionId0 = questionId0;
    }

    public Long getBlogId0() {
        return blogId0;
    }

    public void setBlogId0(Long blogId0) {
        this.blogId0 = blogId0;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public User getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(User receiveUser) {
        this.receiveUser = receiveUser;
    }
}
