package com.lrm.vo;

import com.lrm.po.Blog;
import com.lrm.po.Tag;
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
public class BlogShow {
    public Long id;

    public String title;

    public String description;

    public String tagIds;

    public Long postUserId;

    public Integer view;

    public Integer likesNum;

    public Integer commentsNum;

    public Integer disLikesNum;

    public Integer collectedNum;

    public Integer impact;

    public Date createTime;

    public Date newCommentedTime;

    public String details;

    public String avatar;

    public String wechatPayCode;

    public String nickname;

    public Boolean approved;

    public Boolean disapproved;

    public Boolean collected;

    public String favoriteIds;

    public Boolean origin;

    public String transferStatement;

    public Boolean appreciation;

    public Boolean commentAllowed;

    public Boolean open;

    public Date updateTime;

    public List<Tag> tags = new ArrayList<>();

    public BlogShow() {
    }

    public BlogShow(Blog blog) {
        MyBeanUtils.copyVo(Blog.class, blog, BlogShow.class, this);
        this.postUserId = blog.getUser().getId();
    }

    public static List<BlogShow> getCommentsShow(List<Blog> blogs) {
        List<BlogShow> showList = new ArrayList<>(blogs.size());
        for (Blog blog : blogs) {
            BlogShow show = new BlogShow(blog);
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

    public void setOrigin(Boolean origin) {
        this.origin = origin;
    }

    public void setTransferStatement(String transferStatement) {
        this.transferStatement = transferStatement;
    }

    public void setAppreciation(Boolean appreciation) {
        this.appreciation = appreciation;
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

    public void setCommentAllowed(Boolean commentAllowed) {
        this.commentAllowed = commentAllowed;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setWechatPayCode(String wechatPayCode) {
        this.wechatPayCode = wechatPayCode;
    }
}
