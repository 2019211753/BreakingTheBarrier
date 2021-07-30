package com.lrm.po;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * 踩
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@Entity
@Table(name = "t_dislikes")
public class DisLikes {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 若为true，踩的是评论，否则是false
     */
    private Boolean dislikeComment;
    /**
     * 若为true，踩的是问题，否则是false
     */
    private Boolean dislikeQuestion;


    /**
     * 踩的评论
     */
    @ManyToOne
    private Comment comment;

    /**
     * 踩的问题
     */
    @ManyToOne
    private Question question;

    /**
     * 谁踩的
     */
    @ManyToOne
    private User postUser;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDislikeComment() {
        return dislikeComment;
    }

    public void setDislikeComment(Boolean dislikeComment) {
        this.dislikeComment = dislikeComment;
    }

    public Boolean getDislikeQuestion() {
        return dislikeQuestion;
    }

    public void setDislikeQuestion(Boolean dislikeQuestion) {
        this.dislikeQuestion = dislikeQuestion;
    }

    /**
     * @return 返回前端 不 包含踩的评论
     */
    @JsonBackReference
    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    /**
     * @return 返回前端 不 包含评论所属的问题
     */
    @JsonBackReference
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    /**
     * @return 返回前端 不 包含谁踩的
     */
    @JsonBackReference
    public User getPostUser() {
        return postUser;
    }

    public void setPostUser(User postUser) {
        this.postUser = postUser;
    }

}
