package com.lrm.vo;

import com.lrm.po.*;
import com.lrm.util.MarkdownUtils;
import com.lrm.util.MyBeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 消息处理和评论、点赞的归档
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
     * 发布时间
     */
    public Date createTime;

    /**
     * 评论的内容 如果是点赞的话为null
     * 这里不要用@JsonSerialize注释了，因为我想返回的是不含html标签的纯文本
     */
    //@JsonSerialize(using = ContentSerializerUtils.class)
    public String content;

    /**
     * 当消息处理时 表示为我被点赞或评论的博客标题 或被点赞或评论的评论的内容
     * 当归档时 表示为我点赞或评论的博客标题 或我点赞或评论的内容
     * 如果是评论的内容 会截取一部分
     */
    public String parentContent;

    /**
     * 当消息处理时 表示为我被点赞或评论的类型 如博客被点赞了
     * 当归档时 表示为我点赞或评论的类型 如我点赞了博客
     */
    public String parentType;

    /**
     * 博客或问题、或评论和点赞对应的博客或问题的id 方便点击跳转
     * 当消息处理时 如我被点赞的评论对应的博客的id
     * 当归档时 如我点赞的评论对应的博客的id
     */
    public Long parentId;

    /**
     * parentId对应的发布者的Id
     */
    public Long parentUserId;

    /**
     * 用户id
     * 归档时是动作的被动接受者 消息处理时是发出者 avatar和nickname同理
     */
    public Long userId;

    /**
     * 头像
     */
    public String avatar;

    /**
     * 昵称
     */
    public String nickname;

    public Archive() {
    }

    public Archive(Comment comment, User currentUser) {
        Blog blog;
        Question question;
        Comment parentComment;

        MyBeanUtils.copyVo(Comment.class, comment, Archive.class, this);
        User postUser;
        //如果消息的发出者是不是自己 那么是收到的评论消息
        if ((postUser = comment.getPostUser()) != currentUser) {
            this.nickname = postUser.getNickname();
            this.avatar = postUser.getAvatar();
            this.userId = comment.getPostUser().getId();
        //否则是归档自己发出的评论
        } else  {
            this.nickname = comment.getReceiveUser().getNickname();
            this.avatar = comment.getReceiveUser().getAvatar();
            this.userId = comment.getReceiveUser().getId();
        }

        String text = MarkdownUtils.delHTMLTag(comment.getContent());
        if (text.length() > Magic.BRIEF_COMMENT_COMMENT_LENGTH) {
            this.content = text.substring(0, Magic.BRIEF_COMMENT_COMMENT_LENGTH);
        } else {
            this.content = text;
        }
        this.content += "...";

        //如果是对评论的评论
        if ((parentComment = comment.getParentComment()) != null) {
            //优化父评论的内容
            text = MarkdownUtils.delHTMLTag(parentComment.getContent());
            if (text.length() > Magic.BRIEF_COMMENT_COMMENT_LENGTH) {
                this.parentContent = text.substring(0, Magic.BRIEF_COMMENT_COMMENT_LENGTH);
            } else {
                this.parentContent = text;
            }
            this.parentContent += "...";

            if ((blog = parentComment.getBlog()) != null) {
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

    public Archive(Likes likes, User currentUser) {
        Blog blog;
        Question question;
        Comment parentComment;

        MyBeanUtils.copyVo(Likes.class, likes, Archive.class, this);
        User postUser;

        //如果消息的发出者是不是自己 那么是收到的评论消息
        if ((postUser = likes.getPostUser()) != currentUser) {
            this.nickname = postUser.getNickname();
            this.avatar = postUser.getAvatar();
            this.userId = postUser.getId();
        //否则是归档自己发出的评论
        } else  {
            this.nickname = likes.getReceiveUser().getNickname();
            this.avatar = likes.getReceiveUser().getAvatar();
            this.userId = likes.getReceiveUser().getId();
        }

        if ((parentComment = likes.getComment()) != null) {
            String text = MarkdownUtils.delHTMLTag(parentComment.getContent());
            if (text.length() > Magic.BRIEF_COMMENT_COMMENT_LENGTH) {
                this.parentContent = text.substring(0, Magic.BRIEF_COMMENT_COMMENT_LENGTH);
            } else {
                this.parentContent = text;
            }
            this.parentContent += "...";
            if ((blog = likes.getBlog()) != null) {
                this.parentId = blog.getId();
                this.parentType = "博客的评论";
                this.parentUserId = blog.getUser().getId();

            } else if ((question = likes.getQuestion()) != null) {
                this.parentId = question.getId();
                this.parentType = "问题的评论";
                this.parentUserId = question.getUser().getId();
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

    public static List<Archive> getCommentMessages(List<Comment> comments, User currentUser) {
        List<Archive> archives = new ArrayList<>(comments.size());
        Archive archive;
        for (Comment comment : comments) {
            archive = new Archive(comment, currentUser);
            archives.add(archive);
        }
        return archives;
    }

    public static List<Archive> getLikesMessages(List<Likes> likess, User currentUser) {
        List<Archive> archives = new ArrayList<>(likess.size());
        Archive archive;
        for (Likes likes : likess) {
            archive = new Archive(likes, currentUser);
            archives.add(archive);
        }
        return archives;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
