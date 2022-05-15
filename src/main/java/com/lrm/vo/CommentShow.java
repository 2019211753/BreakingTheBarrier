package com.lrm.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lrm.po.Comment;
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
public class CommentShow {

    public Long id;

    @JsonSerialize(using = ContentSerializerUtils.class)
    public String content;

    public Boolean answer;

    public Boolean adminComment;

    public Boolean selected;

    public Integer likesNum;

    public Integer commentsNum;

    public Integer disLikesNum;

    public Long questionId;

    public Long blogId;

    public Long parentCommentId;

    public Date createTime;

    public String avatar;

    public String nickname;

    public Long postUserId;

    public String parentCommentName;

    public String parentCommentContent;

    public Boolean approved;

    public Boolean disapproved;

    public List<CommentShow> receiveComments;

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    public void setAdminComment(Boolean adminComment) {
        this.adminComment = adminComment;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
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

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public void setParentCommentName(String parentCommentName) {
        this.parentCommentName = parentCommentName;
    }

    public CommentShow() {
    }


    public CommentShow(Comment comment) {
        MyBeanUtils.copyVo(Comment.class, comment, CommentShow.class, this);
        if (comment.getQuestion() != null) {
            this.questionId = comment.getQuestion().getId();
        } else if (comment.getBlog() != null)
        {
            this.blogId = comment.getBlog().getId();
        }
        this.postUserId = comment.getPostUser().getId();
        if (comment.getParentComment() != null) {
            this.parentCommentId = comment.getParentComment().getId();
        } else {
            this.parentCommentId = 0L;
        }
    }

    /**
     * 普通评论
     */
    public static List<CommentShow> getCommentsNormShow(List<Comment> comments) {
        List<CommentShow> showList = new ArrayList<>(comments.size());
        for (Comment comment : comments) {
            CommentShow show = new CommentShow(comment);
            show.receiveComments = getCommentsNormShow(comment.getReceiveComments());
            showList.add(show);
        }

        return showList;
    }

    /**
     * 需要特殊展示出来的评论 如精选评论和最佳评论
     */
    public static List<CommentShow> getCommentsSpecShow(List<Comment> comments) {
        List<CommentShow> showList = new ArrayList<>(comments.size());
        for (Comment comment : comments) {
            CommentShow show = new CommentShow(comment);
            show.parentCommentContent = comment.getParentComment().getContent();
            showList.add(show);
        }
        return showList;
    }






}
