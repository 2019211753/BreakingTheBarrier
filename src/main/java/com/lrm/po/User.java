package com.lrm.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lrm.annotation.AccountInfoFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@Entity
@Table(name = "t_user")
public class User {
    /**
     * 每个类都要有一个id主键
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * NotBlank需要搭配有@Valid的controller方法使用 且只能用在String上
     * 用户昵称
     * 前端必填
     */
    @NotBlank(message = "请输入昵称")
    private String nickname;

    /**
     * 账号 后端校验
     * 前端必填
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @AccountInfoFormat(message = "请输入正确账号格式————长度为7至12且不能包含汉字", need = "false")
    private String username;

    /**
     * 返回Json忽略password
     * 密码
     * 前端必填
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @AccountInfoFormat
    private String password;

    /**
     * 是否为管理员 true为管理员
     */
    private Boolean admin;

    /**
     * 能否发言 true可以发言
     */
    private Boolean canSpeak;

    /**
     * 贡献值
     */
    private Integer donation;

    /**
     * 注册时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date registerTime;

    /**
     * 用户头像 非必填 可以在前端显示默认值
     */
    private String avatar;

    /**
     * 用户邮箱 非必填 可以在前端显示默认值
     */
    private String email;

    /**
     * 用户QQ 非必填 可以在前端显示默认值
     * 这个命名会有隐患么
     */
    private String qqId;

    /**
     * 用户微信 非必填 可以在前端显示默认值
     */
    private String wechatId;

    /**
     * 用户头像 非必填 可以在前端显示默认值
     * 男true 女false
     */
    private Boolean sex;

    /**
     * 个性签名 非必填 可以在前端显示默认值
     */
    private String personalSignature;

    /**
     * 院系 非必填 可以在前端显示默认值
     */
    private String academy;

    /**
     * 专业 非必填 可以在前端显示默认值
     */
    private String major;

    /**
     * 关注了几个人
     */
    private Integer followingUserNum;

    /**
     * 被几个人关注了
     */
    private Integer followedUserNum;

    /**
     * 隐私策略的种类
     * 1.关注/粉丝(following followed) 2.发布问题/评论/点赞 3.收藏夹（open的收藏夹）
     * 111 = 7, 全开放; 110 = 6, 不开放收藏夹; 010 = 2 仅开放发布的问题/评论/点赞
     * 共8种策略
     */
    private Integer privacyType;

    /**
     * 用户可用下载次数
     * 必须默认设为0，否则初始为null，会抛空指针异常
     */
    private Integer availableNum = 0;

    /**
     * 发布的评论
     */
    @OneToMany(mappedBy = "postUser", fetch = FetchType.LAZY)
    private List<Comment> postComments = new ArrayList<>();

    /**
     * 发布的点赞
     */
    @OneToMany(mappedBy = "postUser", fetch = FetchType.LAZY)
    private List<Likes> postLikes = new ArrayList<>();

    /**
     * 没必要设置Remove 因为不打算做注销账号功能
     * 发布的问题
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Question> questions = new ArrayList<>();

    /**
     * 没必要设置Remove 因为不打算做注销账号功能
     * 发布的问题
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Blog> blogs = new ArrayList<>();

    /**
     * 获得的评论
     */
    @JsonIgnore
    @OneToMany(mappedBy = "receiveUser", fetch = FetchType.LAZY)
    private List<Comment> receiveComments = new ArrayList<>();

    /**
     * 接受的点赞
     */
    @JsonIgnore
    @OneToMany(mappedBy = "receiveUser", fetch = FetchType.LAZY)
    private List<Likes> receiveLikes = new ArrayList<>();

    /**
     * 发布的点踩
     */
    @JsonIgnore
    @OneToMany(mappedBy = "postUser", fetch = FetchType.LAZY)
    private List<DisLikes> postDisLikes = new ArrayList<>();

    /**
     * 用户的粉丝
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "followingUsers")
    private List<User> followedUsers = new ArrayList<>();
    /**
     * 用户关注的人
     */
    @JsonIgnore
    @ManyToMany
    private List<User> followingUsers = new ArrayList<>();

    /**
     * 用户的收藏夹
     */
    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<Favorite> favorites = new ArrayList<>();

    /**
     * 用户所上传的文件，这里可以用uploadUser来进行维护
     * 所以添加的时候使用File.uploadUser来进行维护而不是通过User.uploadFiles
     */
    @OneToMany(mappedBy = "uploadUser")
    private List<File> uploadedFiles = new ArrayList<>();

    @ManyToMany(mappedBy = "contributors")
    private List<InfoEntry> infoEntries = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getPersonalSignature() {
        return personalSignature;
    }

    public void setPersonalSignature(String personalSignature) {
        this.personalSignature = personalSignature;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getDonation() {
        return donation;
    }

    public void setDonation(Integer donation) {
        this.donation = donation;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Boolean getCanSpeak() {
        return canSpeak;
    }

    public void setCanSpeak(Boolean canSpeak) {
        this.canSpeak = canSpeak;
    }

    public Integer getFollowingUserNum() {
        return followingUserNum;
    }

    public void setFollowingUserNum(Integer following) {
        this.followingUserNum = following;
    }

    public Integer getFollowedUserNum() {
        return followedUserNum;
    }

    public void setFollowedUserNum(Integer followed) {
        this.followedUserNum = followed;
    }

    public Integer getPrivacyType() {
        return privacyType;
    }

    public void setPrivacyType(Integer privacyType) {
        this.privacyType = privacyType;
    }

    public Integer getAvailableNum() {
        return availableNum;
    }

    public void setAvailableNum(Integer availableNum) {
        this.availableNum = availableNum;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public List<Comment> getPostComments() {
        return postComments;
    }

    public void setPostComments(List<Comment> postComments) {
        this.postComments = postComments;
    }

    public List<Comment> getReceiveComments() {
        return receiveComments;
    }

    public void setReceiveComments(List<Comment> receiveComments) {
        this.receiveComments = receiveComments;
    }

    public List<Likes> getPostLikes() {
        return postLikes;
    }

    public void setPostLikes(List<Likes> postLikes) {
        this.postLikes = postLikes;
    }

    public List<Likes> getReceiveLikes() {
        return receiveLikes;
    }

    public void setReceiveLikes(List<Likes> receiveLikes) {
        this.receiveLikes = receiveLikes;
    }

    public List<DisLikes> getPostDisLikes() {
        return postDisLikes;
    }

    public void setPostDisLikes(List<DisLikes> postDisLikes) {
        this.postDisLikes = postDisLikes;
    }

    public List<User> getFollowedUsers() {
        return followedUsers;
    }

    public void setFollowedUsers(List<User> followedUsers) {
        this.followedUsers = followedUsers;
    }

    public List<User> getFollowingUsers() {
        return followingUsers;
    }

    public void setFollowingUsers(List<User> followingUsers) {
        this.followingUsers = followingUsers;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    public List<File> getUploadedFiles() {
        return uploadedFiles;
    }

    public void setUploadedFiles(List<File> uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }

    public List<InfoEntry> getInfoEntries() {
        return infoEntries;
    }

    public void setInfoEntries(List<InfoEntry> infoEntries) {
        this.infoEntries = infoEntries;
    }
}

