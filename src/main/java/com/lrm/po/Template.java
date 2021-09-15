package com.lrm.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lrm.util.DataStructureUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Blog和Question的公有模板类
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-08-08
 */
@MappedSuperclass
public class Template {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标题
     * 前端必填
     */
    @NotBlank(message = "请输入标题")
    private String title;

    /**
     * 描述
     * 前端必填
     */
    @Lob
    @NotBlank(message = "请输入概述")
    private String description;

    /**
     * 懒加载 只有getContent了才加载
     * 但是前端序列化的时候默认会getContent，所以直接JsonIgnore吧
     * 内容
     * 前端必填
     */
    @JsonIgnore
    @Basic(fetch = FetchType.LAZY)
    @Lob
    @NotBlank(message = "请输入内容")
    private String content;

    /**
     * 前端传回多个标签 用,分割的字符组合 并且存储时要按id顺序重排 搜索时按标签查找需要用到这个属性
     * 前端必填 不允许不存在标签
     */
    @NotBlank(message = "请输入标签")
    private String tagIds;

    /**
     * 前端展示的发布者的Id
     */
    private Long posterUserId0;

    /**
     * 浏览次数
     */
    private Integer view;

    /**
     * 获得点赞数量
     */
    private Integer likesNum;

    /**
     * 获得评论数量
     */
    private Integer commentsNum;

    /**
     * 被点踩数量
     */
    private Integer disLikesNum;

    /**
     * 被收藏数量
     */
    private Integer collectedNum;

    /**
     * 是否被隐藏 true为被隐藏了
     */
    private Boolean isHidden;

    /**
     * 占比待定
     * 影响力
     */
    private Integer impact;

    /**
     * 时间会封装成完整的"yyyy-MM-dd HH:mm:ss"的Date类型
     * 首页展示根据发布时间展示
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 最新被评论时间展示
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date newCommentedTime;


    /**
     * 前端展示的内容 只在需要具体展示内容的时候才展示
     */
    @Transient
    private String details;

    /**
     * 前端展示的头像 在发布和用户修改头像时刷新
     */
    @Transient
    private String avatar;

    /**
     * 前端展示的昵称
     */
    @Transient
    private String nickname;

    /**
     * 返回前端的判断是否被当前用户点过赞
     **/
    @Transient
    private Boolean approved;

    /**
     * 返回前端的判断该是否被当前用户点过踩
     **/
    @Transient
    private Boolean disapproved;

    /**
     * 返回前端的判断该是否被当前用户收藏了
     **/
    @Transient
    private Boolean collected;

    /**
     * 返回前端的博客/问题对应当前用户被收藏夹收藏了的收藏夹Id 以,分割
     */
    @Transient
    private String favoriteIds;


    /**
     * 所在的收藏夹
     */
    @ManyToMany
    @JsonIgnore
    private List<Favorite> favorites = new ArrayList<>();

    /**
     * 发布者
     */
    @JsonIgnore
    @ManyToOne
    private User user;

    /**
     * 标签
     */
    @ManyToMany
    private List<Tag> tags = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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

    public Long getPosterUserId0() {
        return posterUserId0;
    }

    public void setPosterUserId0(Long posterUserId0) {
        this.posterUserId0 = posterUserId0;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public Integer getLikesNum() {
        return likesNum;
    }

    public void setLikesNum(Integer likesNum) {
        this.likesNum = likesNum;
    }

    public Integer getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(Integer commentsNum) {
        this.commentsNum = commentsNum;
    }

    public Integer getCollectedNum() {
        return collectedNum;
    }

    public void setCollectedNum(Integer collectedNum) {
        this.collectedNum = collectedNum;
    }

    public Integer getDisLikesNum() {
        return disLikesNum;
    }

    public void setDisLikesNum(Integer disLikesNum) {
        this.disLikesNum = disLikesNum;
    }

    public Boolean getHidden() {
        return isHidden;
    }

    public void setHidden(Boolean hidden) {
        isHidden = hidden;
    }

    public Integer getImpact() {
        return impact;
    }

    public void setImpact(Integer impact) {
        this.impact = impact;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getNewCommentedTime() {
        return newCommentedTime;
    }

    public void setNewCommentedTime(Date newCommentedTime) {
        this.newCommentedTime = newCommentedTime;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean getDisapproved() {
        return disapproved;
    }

    public void setDisapproved(Boolean disapproved) {
        this.disapproved = disapproved;
    }

    public Boolean getCollected() {
        return collected;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    public String getFavoriteIds() {
        return favoriteIds;
    }

    public void setFavoriteIds(String favoriteIds) {
        this.favoriteIds = favoriteIds;
    }

    /**
     * 真正起setTagIds作用的是这个方法
     * Tag集合转为String对象
     */
    public void init() {
        this.tagIds = DataStructureUtils.tagsToIds(this.getTags());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Template)) {
            return false;
        }
        Template template = (Template) o;
        return Objects.equals(getId(), template.getId()) && Objects.equals(getTitle(), template.getTitle()) && Objects.equals(getDescription(), template.getDescription()) && Objects.equals(getContent(), template.getContent()) && Objects.equals(getTagIds(), template.getTagIds()) && Objects.equals(getPosterUserId0(), template.getPosterUserId0()) && Objects.equals(getView(), template.getView()) && Objects.equals(getLikesNum(), template.getLikesNum()) && Objects.equals(getCommentsNum(), template.getCommentsNum()) && Objects.equals(getDisLikesNum(), template.getDisLikesNum()) && Objects.equals(isHidden, template.isHidden) && Objects.equals(getImpact(), template.getImpact()) && Objects.equals(getCreateTime(), template.getCreateTime()) && Objects.equals(getNewCommentedTime(), template.getNewCommentedTime()) && Objects.equals(getAvatar(), template.getAvatar()) && Objects.equals(getNickname(), template.getNickname()) && Objects.equals(getApproved(), template.getApproved()) && Objects.equals(getDisapproved(), template.getDisapproved()) && Objects.equals(getCollected(), template.getCollected()) && Objects.equals(getFavorites(), template.getFavorites()) && Objects.equals(getUser(), template.getUser()) && Objects.equals(getTags(), template.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getContent(), getTagIds(), getAvatar(), getNickname(), getPosterUserId0(), getView(), getLikesNum(), getCommentsNum(), getDisLikesNum(), isHidden, getImpact(), getCreateTime(), getNewCommentedTime(), getApproved(), getDisapproved(), getCollected(), getFavorites(), getUser(), getTags());
    }


    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }
}
