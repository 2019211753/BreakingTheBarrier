package com.lrm.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lrm.po.Blog;
import com.lrm.po.Comment;
import com.lrm.po.Likes;
import com.lrm.po.Question;
import com.lrm.util.ContentSerializerUtils;
import com.lrm.util.MyBeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 消息处理和评论的归档
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2022-01-10
 */
public class Archive {
    /**
     * 消息Id 即点赞或评论的Id
     */
    public Long id;

    /**
     * 消息的发布时间
     */
    public Date createTime;

    /**
     * 消息的内容 如果是点赞消息的话为null
     */
    @JsonSerialize(using = ContentSerializerUtils.class)
    public String content;

    /**
     * 消息的被动接受者的内容 如被点赞或评论的博客标题 或评论的内容
     * 这里不方便截取一部分，因为如果截断了base64编码的图片的话会出现乱码
     */
    public String parentContent;

    /**
     * 消息的被动接受者的类型 如博客、问题、评论
     */
    public String parentType;

    /**
     * 消息的被动接受者（如博客或问题）的id 方便点击跳转
     */
    public Long parentId;

    /**
     * 被动接受者的发布者的id
     */
    public Long parentUserId;

    /**
     * 消息发送者的头像
     */
    public String avatar;

    /**
     * 消息发送者的昵称
     */
    public String nickname;

    public Archive() {
    }

    public Archive(Comment comment) {
        Blog blog;
        Question question;
        Comment parentComment;

        MyBeanUtils.copyVo(Comment.class, comment, Archive.class, this);

        this.content = comment.getContent();
        if ((parentComment = comment.getParentComment()) != null) {
            this.parentContent = parentComment.getContent();
            if ((blog = comment.getBlog()) != null) {
                this.parentId = blog.getId();
                this.parentUserId = blog.getUser().getId();
                this.parentType = "博客的评论";
            } else if ((question = comment.getQuestion()) != null) {
                this.parentId = question.getId();
                this.parentUserId = question.getUser().getId();
                this.parentType = "问题的评论";
            }
        } else if ((blog = comment.getBlog()) != null) {
            this.parentContent = blog.getTitle();
            this.parentType = "博客";
            this.parentId = blog.getId();
            this.parentUserId = blog.getUser().getId();
        } else if ((question = comment.getQuestion()) != null) {
            this.parentContent = question.getTitle();
            this.parentType = "问题";
            this.parentId = question.getId();
            this.parentUserId = question.getUser().getId();
        }
    }

    public Archive(Likes likes) {
        Blog blog;
        Question question;
        Comment parentComment;

        this.id = likes.getId();
        this.createTime = likes.getCreateTime();
        this.avatar = likes.getPostUser().getAvatar();
        this.nickname = likes.getPostUser().getNickname();

        if ((parentComment = likes.getComment()) != null) {
            this.parentContent = parentComment.getContent();
            if ((blog = likes.getBlog()) != null) {
                this.parentId = blog.getId();
                this.parentUserId = blog.getUser().getId();
                this.parentType = "博客的评论";
            } else if ((question = likes.getQuestion()) != null) {
                this.parentId = question.getId();
                this.parentUserId = question.getUser().getId();
                this.parentType = "问题的评论";
            }
        } else if ((blog = likes.getBlog()) != null) {
            this.parentContent = blog.getTitle();
            this.parentType = "博客";
            this.parentId = blog.getId();
            this.parentUserId = blog.getUser().getId();
        } else if ((question = likes.getQuestion()) != null) {
            this.parentContent = question.getTitle();
            this.parentType = "问题";
            this.parentId = question.getId();
            this.parentUserId = question.getUser().getId();
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public static List<Archive> getCommentMessages(List<Comment> comments) {
        List<Archive> archives = new ArrayList<>(comments.size());
        Archive archive;
        for (Comment comment : comments) {
            archive = new Archive(comment);
            archives.add(archive);
        }
        return archives;
    }

    public static List<Archive> getLikesMessages(List<Likes> likess) {
        List<Archive> archives = new ArrayList<>(likess.size());
        Archive archive;
        for (Likes likes : likess) {
            archive = new Archive(likes);
            archives.add(archive);
        }
        return archives;
    }
}
