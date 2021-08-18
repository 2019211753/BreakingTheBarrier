package com.lrm.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 问题
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@Entity
@Table(name = "t_question")
public class Question extends Template {

    /**
     * 问题拥有的赞
     */
    @JsonIgnore
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Likes> likes;

    /**
     * 问题拥有的踩
     */
    @JsonIgnore
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<DisLikes> dislikes;

    /**
     * 问题的子评论
     */
    @JsonIgnore
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Question question = (Question) o;
        return Objects.equals(getLikes(), question.getLikes()) && Objects.equals(getDislikes(), question.getDislikes()) && Objects.equals(getComments(), question.getComments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLikes(), getDislikes(), getComments());
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
