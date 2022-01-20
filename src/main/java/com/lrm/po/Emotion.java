package com.lrm.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Likes和DisLikes的共有父类 情绪类
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-08-09
 */
@MappedSuperclass
public class Emotion {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;


    /**
     * 踩/赞的评论
     */
    @JsonIgnore
    @ManyToOne
    private Comment comment;

    /**
     * 踩/赞的问题
     */
    @JsonIgnore
    @ManyToOne
    private Question question;

    /**
     * 踩/赞的博客
     */
    @JsonIgnore
    @ManyToOne
    private Blog blog;

    /**
     * 谁踩/赞的
     */
    @JsonIgnore
    @ManyToOne
    private User postUser;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public User getPostUser() {
        return postUser;
    }

    public void setPostUser(User postUser) {
        this.postUser = postUser;
    }
}
