package com.lrm.po;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
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
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 懒加载 只有getContent了才加载
     * 前端必填内容
     */
    @Basic(fetch = FetchType.LAZY)
    @Lob
    @NotBlank(message = "请输入问题内容")
    private String content;

    /**
     * 问题描述 前端必填
     */
    @Lob
    @NotBlank(message = "请输入问题概述")
    private String description;

    /**
     * 问题标题 前端必填
     **/
    @NotBlank(message = "请输入问题标题")
    private String title;

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
     **/
    private Integer commentsNum;

    /**
     * 被点踩数量
     */
    private Integer disLikesNum;

    /**
     * 是否被隐藏 true为被隐藏了
     */
    private Boolean isHidden;

    /**
     * 占比待定
     * 问题的影响力 推荐 impact=user.donation*4+question.view*2+question.comment.count*2+question.comment.maxLikes*2
     * user.donation=user.comment2.count*3++user.comment2.likes*3+user.question.count*2+user.question.likes*2
     */
    private Integer impact;

    /**
     * 时间会封装成完整的"yyyy-MM-dd HH:mm:ss"的Date类型
     * 首页展示根据发布时间展示
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 最新被评论时间展示
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date newCommentedTime;

    /**
     * 前端传回多个标签 用,分割的字符组合 并且存储时要按id顺序重排 搜索时按标签查找需要用到这个属性
     **/
    private String tagIds;


    /**
     * 节约空间不入库
     * 返回前端的问题发布者的头像
     **/
    @Transient
    private String avatar;

    /**
     * 节约空间不入库
     * 返回前端的问题发布者的昵称
     **/
    @Transient
    private String nickname;

    /**
     * 节约空间不入库
     * 返回前端的判断该问题是否被当前用户点过赞
     **/
    @Transient
    private Boolean approved;

    /**
     * 节约空间不入库
     * 返回前端的判断该问题是否被当前用户点过踩
     **/
    @Transient
    private Boolean disapproved;

    /**
     * 节约空间不入库
     * 返回前端的判断该问题是否被当前用户收藏了
     **/
    @Transient
    private Boolean collected;


    /**
     * 问题的标签
     */
    @ManyToMany
    private List<Tag> tags = new ArrayList<>();

    /**
     * 问题的发布者
     **/
    @ManyToOne
    private User user;

    /**
     * 问题拥有的赞
     */
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Likes> likes;

    /**
     * 问题拥有的踩
     */
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<DisLikes> dislikes;

    /**
     * 问题的子评论
     */
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    /**
     * 问题所在的收藏夹
     */
    @ManyToMany
    private List<Favorite> favorites;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public Integer getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(Integer commentsNum) {
        this.commentsNum = commentsNum;
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

    public Integer getLikesNum() {
        return likesNum;
    }

    public void setLikesNum(Integer likesNum) {
        this.likesNum = likesNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Integer getImpact() {
        return impact;
    }

    public void setImpact(Integer impact) {
        this.impact = impact;
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

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    /**
     * 真正起setTagIds作用的是这个方法
     * Tag集合转为String对象
     */
    public void init() {
        this.tagIds = tagsToIds(this.getTags());
    }

    /**
     * 前端Tag对象的格式是以,分割的 tagIds作为一个媒介
     *
     * @param tags 与question关联的tag集合
     * @return , 分割的tagid
     */
    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuilder ids = new StringBuilder();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        Question question = (Question) o;
        return Objects.equals(getId(), question.getId()) && Objects.equals(getContent(), question.getContent()) && Objects.equals(getDescription(), question.getDescription()) && Objects.equals(getTitle(), question.getTitle()) && Objects.equals(getView(), question.getView()) && Objects.equals(getLikesNum(), question.getLikesNum()) && Objects.equals(getCommentsNum(), question.getCommentsNum()) && Objects.equals(getDisLikesNum(), question.getDisLikesNum()) && Objects.equals(isHidden, question.isHidden) && Objects.equals(getImpact(), question.getImpact()) && Objects.equals(getCreateTime(), question.getCreateTime()) && Objects.equals(getNewCommentedTime(), question.getNewCommentedTime()) && Objects.equals(getTagIds(), question.getTagIds()) && Objects.equals(getAvatar(), question.getAvatar()) && Objects.equals(getNickname(), question.getNickname()) && Objects.equals(getApproved(), question.getApproved()) && Objects.equals(getDisapproved(), question.getDisapproved()) && Objects.equals(getCollected(), question.getCollected()) && Objects.equals(getTags(), question.getTags()) && Objects.equals(getUser(), question.getUser()) && Objects.equals(getLikes(), question.getLikes()) && Objects.equals(getDislikes(), question.getDislikes()) && Objects.equals(getComments(), question.getComments()) && Objects.equals(getFavorites(), question.getFavorites());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getContent(), getDescription(), getTitle(), getView(), getLikesNum(), getCommentsNum(), getDisLikesNum(), isHidden, getImpact(), getCreateTime(), getNewCommentedTime(), getTagIds(), getAvatar(), getNickname(), getApproved(), getDisapproved(), getCollected(), getTags(), getUser(), getLikes(), getDislikes(), getComments(), getFavorites());
    }

    /**
     * @return 返回前端保留问题的标签
     */
    @JsonManagedReference
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * @return 返回前端 不 保留发布者的信息
     */
    @JsonBackReference
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return 返回前端 不 保留问题下属评论
     */
    @JsonBackReference
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * @return 返回前端 不 保留收到的点赞
     */
    @JsonBackReference
    public List<Likes> getLikes() {
        return likes;
    }

    public void setLikes(List<Likes> likes) {
        this.likes = likes;
    }

    /**
     * @return 返回前端 不 保留问题的被踩
     */
    @JsonBackReference
    public List<DisLikes> getDislikes() {
        return dislikes;
    }

    public void setDislikes(List<DisLikes> dislikes) {
        this.dislikes = dislikes;
    }

    /**
     * @return 返回前端 不 保留问题所属的收藏夹
     */
    @JsonBackReference
    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }
}
