package com.lrm.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lrm.po.Question;
import com.lrm.util.ContentSerializerUtils;
import com.lrm.util.MyBeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 类<code>Doc</code>用于：TODO
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2022-01-19
 */
public class QuestionShow {
    public Long id;

    public String title;

    public String description;

    @JsonSerialize(using = ContentSerializerUtils.class)
    public String content;

    public String tagIds;

    public Long postUserId;

    public Integer view;

    public Integer likesNum;

    public Integer commentsNum;

    public Integer disLikesNum;

    public Integer collectedNum;

    public Integer solvedNum;

    public Boolean solved;

    public Integer impact;

    public Date createTime;

    public Date newCommentedTime;

    public String details;

    public String avatar;

    public String nickname;

    public Boolean approved;

    public Boolean disapproved;

    public Boolean collected;

    public String favoriteIds;

    public QuestionShow() {
    }

    public QuestionShow(Question question) {
        MyBeanUtils.copyVo(Question.class, question, QuestionShow.class, this);
        this.postUserId = question.getUser().getId();
    }

    public static List<QuestionShow> getCommentsShow(List<Question> questions) {
        List<QuestionShow> showList = new ArrayList<>(questions.size());
        for (Question question : questions) {
            QuestionShow show = new QuestionShow(question);
            showList.add(show);
        }
        return showList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public void setLikesNum(Integer likesNum) {
        this.likesNum = likesNum;
    }

    public void setCommentsNum(Integer commentsNum) {
        this.commentsNum = commentsNum;
    }

    public void setDisLikesNum(Integer disLikesNum) {
        this.disLikesNum = disLikesNum;
    }

    public void setCollectedNum(Integer collectedNum) {
        this.collectedNum = collectedNum;
    }

    public void setImpact(Integer impact) {
        this.impact = impact;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setNewCommentedTime(Date newCommentedTime) {
        this.newCommentedTime = newCommentedTime;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public void setDisapproved(Boolean disapproved) {
        this.disapproved = disapproved;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    public void setFavoriteIds(String favoriteIds) {
        this.favoriteIds = favoriteIds;
    }

    public void setSolvedNum(Integer solvedNum) {
        this.solvedNum = solvedNum;
    }

    public void setSolved(Boolean solved) {
        this.solved = solved;
    }
}
