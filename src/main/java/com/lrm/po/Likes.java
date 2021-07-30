package com.lrm.po;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
public class Likes {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 赞的是评论
     */
    private Boolean likeComment;
    /**
     * 赞的是问题
     */
    private Boolean likeQuestion;

    /**
     * 是否已读
     * 不能用isRead 也不能用read关键字 只能这样了。。。
     */
    private Boolean looked;

    /**
     * 返回前端的点赞发布者Id
     * 返回user对象被json忽略 只能加个这个了
     */
    private Long postUserId0;

    /**
     * 返回前端的点赞所在问题Id
     * 返回question对象被json忽略 只能加个这个了
     */
    private Long questionId0;

    /**
     * 封装成完整的"yyyy-MM-dd HH:mm:ss"的Date类型
     * 点赞时间
     */
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
     * 赞的评论
     */
    @ManyToOne
    private Comment comment;

    /**
     * 赞的问题
     */
    @ManyToOne
    private Question question;

    /**
     * 谁赞的
     */
    @ManyToOne
    private User postUser;
    /**
     * 赞的谁
     */
    @ManyToOne
    private User receiveUser;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getLikeComment() {
        return likeComment;
    }

    public void setLikeComment(Boolean likeComment) {
        this.likeComment = likeComment;
    }

    public Boolean getLikeQuestion() {
        return likeQuestion;
    }

    public void setLikeQuestion(Boolean likeQuestion) {
        this.likeQuestion = likeQuestion;
    }

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


    /**
     * @return 返回前端包含点赞所属的评论
     */
    @JsonManagedReference
    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    /**
     * @return 返回前端包含点赞所属的问题
     */
    @JsonManagedReference
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    /**
     * @return 返回前端 不 包含谁点的赞
     */
    @JsonBackReference
    public User getPostUser() {
        return postUser;
    }

    public void setPostUser(User postUser) {
        this.postUser = postUser;
    }

    /**
     * @return 返回前端 不 包含谁被点赞了
     */
    @JsonBackReference
    public User getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(User receiveUser) {
        this.receiveUser = receiveUser;
    }
}
