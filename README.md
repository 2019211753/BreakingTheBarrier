[TOC]



## 实体类属性

### 评论类 Comment

```java
/**
 * 主键
 */
Long id;

/**
 * 父级评论Id 如果是第一级评论即为-1
 * 前端必填
 */
Long parentCommentId0;

/**
 * 评论内容
 * 前端必填
 */
String content;

/**
 * 是否是第二类回答 即正式回答 true为正式回答
 * 前端必填
 */
Boolean answer;

/**
 * 如果标识为1 即发布这个comment的人也是帖子的发布者 那么它应该有一个类似楼主的标志
 */
Boolean adminComment;

/**
 * 通知是否已读 true为已读
 */
Boolean looked;

/**
 * 点赞数
 */
Integer likesNum;

/**
 * 被评论数
 */
Integer commentsNum;

/**
 * 点踩数
 */
Integer disLikesNum;

/**
 * 是否被隐藏
 */
Boolean isHidden;

/**
 * 评论发布者Id
 */
Long postUserId0;

/**
 * 评论对应问题Id
 */
Long questionId0;

/**
 * 评论对应博客Id
 */
Long blogId0;

/**
 * 封装成完整的"yyyy-MM-dd HH:mm:ss"的Date类型
 * 评论时间
 */
Date createTime;

/**
 * 返回前端的评论发布者的头像
 */
String avatar;

/**
 * 返回前端的评论发布者的昵称
 */
String nickname;

/**
 * 返回前端的父级评论发布者的昵称
 */
String parentCommentName;

/**
 * 返回前端的判断该评论是否被当前用户点过赞 true为点过赞
 */
Boolean approved;

/**
 * 返回前端的判断该评论是否被当前用户点过踩 true为点过踩
 */
Boolean disapproved;


/**
 * 评论的子评论
 */
List<Comment> receiveComments = new ArrayList<>();
```

### 点踩类 DisLikes

```java
/**
 * 主键
 */
Long id;
```

### 点赞类 Likes

```java
/**
 * 主键
 */
Long id;

/**
 * 是否已读
 */
Boolean looked;

/**
 * 返回前端的点赞发布者Id
 */
Long postUserId0;

/**
 * 返回前端的点赞所在问题Id
 */
Long questionId0;

/**
* 返回前端的点赞所在问题Id
*/
Long blogId0;

/**
 * 封装成完整的"yyyy-MM-dd HH:mm:ss"的Date类型
 * 点赞时间
 */
Date createTime;

/**
 * 返回前端的评论发布者的头像
 */
String avatar;

/**
 * 返回前端的评论发布者的昵称
 */
String nickname;
```

### 收藏夹类 Favorite

```java
Long id;

/**
 * 收藏夹的名字 
 * 前端必填
 */
String title;

/**
 * 是否公开 true为公开 个人主页被其它用户访问时可以看到
 * 前端必填
 */
Boolean open;

/**
 * 创建收藏夹的时间
 */
@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
Date createTime;

/**
 * 上一次添加收藏的时间
 */
@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
Date updateTime;

/**
 * 收藏几个了
 */
int size;

/**
 * 收藏夹的上限容量
 */
public static final int MAXSIZE = 25;
```

### 问题类 Question

```java
Long id;

/**
 * 问题标题 
 * 前端必填
 */
String title;

/**
 * 问题描述 
 * 前端必填
 */
String description;

/**
 * 问题内容 不会返回到前端
 * 前端必填
 */
String content;

/**
 * 问题内容 content的替代品，会返回到前端
 *
 */
String details;

/**
 * 前端传回多个标签 用,分割的字符组合 并且存储时要按id顺序重排 搜索时按标签查找需要用到这个属性
 * 前端必填 不允许不存在标签
 */
String tagIds;

/**
* 发布者的Id
*/
 Long posterUserId0;

/**
 * 浏览次数
 */
 Integer view;

/**
 * 获得点赞数量
 */
 Integer likesNum;

/**
 * 获得评论数量
 */
Integer commentsNum;

/**
 * 被点踩数量
 */
Integer disLikesNum;

/**
 * 被收藏数量
 */ 
Integer commentsNum;

/**
 * 是否被隐藏 true为被隐藏了
 */
Boolean isHidden;

/**
 * 占比待定
 * 问题的影响力
 */
Integer impact;

/**
 * 时间会封装成完整的"yyyy-MM-dd HH:mm:ss"的Date类型
 * 首页展示根据发布时间展示
 */
Date createTime;

/**
 * 最新被评论时间展示
 */
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
Date newCommentedTime;

/**
 * 返回前端的问题发布者的头像
 */
String avatar;

/**
 * 返回前端的问题发布者的昵称
 **/
String nickname;

/**
 * 返回前端的判断该问题是否被当前用户点过赞
 **/
Boolean approved;

/**
 * 返回前端的判断该问题是否被当前用户点过踩
 */
Boolean disapproved;

/**
 * 返回前端的判断该问题是否被当前用户收藏了
 */
Boolean collected;

/**
 * 与tagIds相似，以,分割的，对应当前用户收藏夹id的字符串
 */
Boolean favoriteIds;


/**
 * 问题的标签
 */
List<Tag> tags = new ArrayList<>();
```

### 博客类 Blog

```java
Long id;

/**
 * 博客标题 
 * 前端必填
 */
String title;

/**
 * 博客描述
 * 前端必填
 */
String description;

/**
 * 博客内容 不会返回到前端
 * 前端必填
 */
String content;

/**
 * 博客内容 content的替代品，会返回到前端
 *
 */
String details;

/**
 * 前端传回多个标签 用,分割的字符组合 并且存储时要按id顺序重排 搜索时按标签查找需要用到这个属性 如1,2,3 只能用,分割 不能有空格
 * 前端必填 不允许不存在标签
 */
String tagIds;

/**
 * 博客是否原创 
 * 前端必填
 */
Boolean origin;

/**
* 如果不是原创 在此注明作者和来源 前端标注在特殊位置 不要跟content在一块
*/
String transferStatement;

/**
* 是否接受赞赏 
* 前端必填
*/
Boolean appreciation;

/**
* 是否允许评论 
* 前端必填
*/
Boolean commentAllowed;

/**
* 是否公开发表 
* 前端必填
*/
Boolean open;

/**
 * 发布者的Id
 */
Long posterUserId0;

/**
 * 浏览次数
 */
Integer view;

/**
 * 获得点赞数量
 */
Integer likesNum;

/**
 * 获得评论数量
 */
Integer commentsNum;

/**
 * 被点踩数量
 */
Integer disLikesNum;

/**
 * 被收藏数量
 */ 
Integer commentsNum;

/**
 * 是否被隐藏 true为被隐藏了
 */
Boolean isHidden;

/**
 * 占比待定
 * 博客的影响力
 */
Integer impact;

/**
 * 时间会封装成完整的"yyyy-MM-dd HH:mm:ss"的Date类型
 * 首页展示根据发布时间展示
 */
Date createTime;

/**
* 博客更新时间
*/
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
Date updateTime;

/**
 * 最新被评论时间展示
 */
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
Date newCommentedTime;


/**
 * 返回前端的博客发布者的头像
 */
String avatar;

/**
 * 返回前端的博客发布者的昵称
 **/
String nickname;

/**
 * 返回前端的判断该博客是否被当前用户点过赞
 **/
Boolean approved;

/**
 * 返回前端的判断该博客是否被当前用户点过踩
 */
Boolean disapproved;

/**
 * 返回前端的判断该博客是否被当前用户收藏了
 */
Boolean collected;


/**
 * 博客的标签
 */
List<Tag> tags = new ArrayList<>();
```

### 标签类 Tag

```java
/**
 * 主键
 */
Long id;

/**
 * 父级标签Id 
 * 前端必填
 */
Long parentTagId0;

/**
 * 标签名称 
 * 前端必填
 */
String name;

/**
 * 标签的子标签
 */
List<Tag> childTags = new ArrayList<>();
```

### 用户类 User

```java
/**
 * 每个类都要有一个id主键
 */
Long id;

/**
 * 用户昵称
 * 前端必填
 */
String nickname;

/**
 * 账号
 * 前端必填 
 */
String username;

/**
 * 密码
 * 前端必填
 */
String password;

/**
 * 是否为管理员 true为管理员
 */
Boolean admin;

/**
 * 能否发言 true可以发言
 */
Boolean canSpeak;

/**
 * 贡献值
 */
Integer donation;

/**
 * 注册时间
 */
@Temporal(TemporalType.TIMESTAMP)
Date registerTime;

/**
 * 用户头像 非必填 可以在前端显示默认值
 */
String avatar;

/**
 * 用户邮箱 非必填 可以在前端显示默认值
 */
String email;

/**
 * 用户QQ 非必填 可以在前端显示默认值
 * 这个命名会有隐患么
 */
String qqId;

/**
 * 用户微信 非必填 可以在前端显示默认值
 */
String wechatId;

/**
 * 用户头像 非必填 可以在前端显示默认值
 * 男true 女false
 */
Boolean sex;

/**
 * 个性签名 非必填 可以在前端显示默认值
 */
String personalSignature;

/**
 * 院系 非必填 可以在前端显示默认值
 */
String academy;

/**
 * 专业 非必填 可以在前端显示默认值
 */
String major;

/**
 * 关注了几个人
 */
Integer followingUserNum;

/**
 * 被几个人关注了
 */
Integer followedUserNum;

/**
 * 隐私策略的种类
 * 1.关注/粉丝(following followed) 2.发布问题/评论/点赞 3.收藏夹（open的收藏夹）
 * 111 = 7, 全开放; 110 = 6, 不开放收藏夹; 010 = 2 仅开放发布的问题/评论/点赞
 * 共8种策略
 */
Integer privacyType;

/**
 * 发布的评论
 */
List<Comment> postComments = new ArrayList<>();

/**
 * 发布的点赞
 */
List<Likes> postLikes = new ArrayList<>();

/**
 * 用户所上传的文件
 */
List<File> uploadedFiles = new ArrayList<>();
```

### 文件标签类 FileTag

```java
/**
* id主键
*/
Long id;

/**
* 标签名称 前端必填
*/
@NotBlank(message = "请输入标签名称")
String name;
/**
* 标签下的文件 
*/
List<File> files = new ArrayList<>();

```

### 文件类File

```java
//主键
Long id;

//文件名
String name;

//文件存放 路径
String path;

//文件大小
String size;

//下载次数
Integer downloadCount = 0;

//浏览或搜索次数
Integer viewCount = 0;

// 对应的标签类型
List<FileTag> fileTags = new ArrayList<>();

//文件的上传用户
User uploadUser;
```



## 返回数据格式

```java
//封装好的返回数据 一般是Map类型
T data;

/*
 * 407 定义为前端发送的请求方式有误 如POST写成GET 不需要对用户展示了 只提供给开发人员 方便debug
 * 406 定义为操作失败 如改名已有同名昵称了所以没改成功等等（不是跳转到异常页面，而是直接在当前页给出提示）
 * 405 定义为参数校验失败 如登录没写密码（不是跳转到异常页面，而是直接在当前页给出提示）
 * 404 定义为没找到对应资源
 * 403 定义为无权限访问
 * 402 定义为文件过大或输入输出流异常
 * 401 定义为JWT鉴权失败
 * 400 定义其他未知错误
 * 200 为操作成功
 */
Integer code;

//成功或报错信息
String msg;

//报错时，某些情况会返回请求的API路径
StringBuffer url;
```

需要前端全局异常捕获。视情景使用返回的data和打印出msg。

## API

如果是测试服务器上API，注意前面加上39.105.212.210/；

所有请求都要在请求头前加上token，token仅在修改信息和登录时返回；

绝大多数请求都有返回值，data、msg和url可能为null，但code一定不为null，所以一定要接收返回值；

@param 后为需要传递到后端的值，变量名需要完全一致；

@return data 后为操作成功返回的值，操作不成功返回msg，code不为null，url和data可能不为null，视情景而定。此外，需要对后端抛出的异常进行捕获和处理。

### 管理员

#### 问题管理

##### 返回某问题

```java
/**
 * @param questionId 被编辑的问题Id
 * @return data：{ "template" : question,
 				"tags", tags}
 * template为要修改的Question对象，tags是一个树，包含所有标签
 */
@GetMapping("/admin/question/{questionId}/edit")

```

##### 修改问题

```java
/**
 * @param question 被编辑的Question对象
 * @return data：{"question" : question}
 *
 * question为被修改了的Question对象
 */
@PostMapping("/admin/question/post")

```

##### 删除问题

```java
与用户删除问题用一个API
```

##### 搜索问题

```java
/**
 * 管理页根据标题、标签、用户昵称搜索 前端传入Question对象
 *
 * @param pageable 分页对象
 * @param queryMap : {"tagIds" : tagIds, "query" : query, "nickname" : "nickname"}
 * @return data：{"pages", pages}
 *
 * query为关键字 nickname为用户昵称
 */
@PostMapping("/admin/searchQuestions")
```

#### 博客管理

##### 删除博客

```java
与用户删除博客用一个API
```

#####  搜索博客

```java
/**
 * 类上
 */
@PostMapping("/admin/searchBlogs")
```



#### 标签管理

##### 增加/修改标签

```java
/**
 * @param tag 前端封装好的Tag对象
 * @return data: {"tag": tag}
 *
 * tag为已保存的Tag对象
 */
@PostMapping("/admin/tags/input")
```

##### 删除标签

```java
/**
 * 删除标签
 *
 * @param tagId 标签id
 * @return data：{null}
 */
@GetMapping("/admin/tags/{tagId}/delete")
```

#### 用户管理

##### 用户查找

```java
/**
 * @param nickname 昵称
 * @return data：{"user": user}
 *
 * user为查询得到的用户
 */
@PostMapping("/admin/searchUser")
```

##### 用户解禁言

```java
/**
 * 禁言/解禁
 *
 * @param userId 用户Id
 * 无返回值 但可能抛出异常 所以也要获取返回值看看有没有报错
 * 下面的API没有返回值的同样这样处理
 */
@GetMapping("/admin/controlSpeak/{userId}")
```

### 普通用户

#### 归档

##### 问题归档

```java
/**
 * 按年份归档 时间逆序
 *
 * @return data：{"archiveMap": Map<String, Map<String, Object>>类型的Question集合, "count": 集合中question的数量}
 */
@GetMapping("/customer/archivesQuestion")
```

##### 博客归档

```java
/**
 * 类上
 */
@GetMapping("/customer/archivesBlog")
```

#### 收藏夹管理

##### 创建收藏夹

```java
/**
 * 创建收藏夹
 *
 * @param favorite	前端传回favorite对象
 * @return data：{"favorite": favorite}
 * 
 * favorite为创建好的收藏夹
 */
@PostMapping("/customer/favorite/create")
```

##### 删除收藏夹

```java
/**
 * 删除收藏夹
 *
 * @param favoriteId 收藏夹的Id
 * @return data：{null}
 */
@GetMapping("/customer/favorite/{favoriteId}/delete")
```

##### 修改收藏夹

```java
 /**
* 更新收藏夹名称和是否公开
* @param favorite0 前端封装好的收藏夹
* @param request获取Id
* @return data: {"favorite": favorite}
*
* favorite为修改后的收藏夹
*/
@PostMapping("/customer/favorite/edit")
```

##### 展示收藏夹

```java
/**
 * 展示用户的所有收藏夹
 *
 * @return data：{"favorites": favorites}
 *
 * favorites为收藏夹的List集合
 */
@GetMapping("/customer/favorites")
```

##### 展示某收藏夹的内容

```java
/**
 * 展示某收藏夹的内容
 *
 * @param favoriteId 收藏夹Id
 * @return data：{"questions": questions, "blogs": blogs}
 *
 * questions为收藏夹下问题的List集合
 * blogs为收藏夹下博客的list集合
 */
@GetMapping("/customer/favorite/{favoriteId}")
```

##### 收藏或移除问题

```java
/**
 * 收藏或移除问题
 *
 * @param questionId 问题Id
 * @param favoriteId 收藏夹Id
 * @param request获取当前用户Id
 * @param pageable 为了方便调用方法的 不想再造了
 * @return data：{"collectedNum" : collectedNum, "collected", collected}
 * collectedNum为该问题被收藏多少次了，collected为我现在有没有收藏它
 */
@GetMapping("/customer/favorite/{favoriteId}/modify/question/{questionId}"）
```

##### 收藏或移除博客

```java
/**
 * 同上
 */
@GetMapping("/customer/favorite/{favoriteId}/modify/blog/{blogId}")
```

#### 登陆、注册

#### 消息通知管理

##### 返回所有通知

```java
**
 * 返回所有通知
 *
 * @return data：{"lookedComments": lookedComments, 
 "unLookedComments": unLookedComments,
 "lookedLikes": lookedLikes,
 "unLookedLikes": unLookedLikes}
 * 都是List集合 
 */
@GetMapping("/customer/messages/")
```

##### 已读某评论

```java
/**
 * 已读单个评论
 *
 * @param commentId 评论id
 * @return data：{null}
 */
@GetMapping("/customer/messages/comment/{commentId}/read")
```

##### 已读某点赞

```java
/**
 * 已读单个点赞
 *
 * @param likesId 点赞id
 * @return data：{null}
 */
@GetMapping("/customer/messages/likes/{likesId}/read")
```

##### 已读所有未读的评论

```java
/**
 * 已读所有未读的评论
 *
 * @return data：{null}
 */
@GetMapping("/customer/messages/readAllComments")
```

##### 已读所有未读的点赞

```java
/**
 * 已读所有未读的点赞
 *
 * @return data：{null}
 */
@GetMapping("/customer/messages/readAllLikes")
```

#### 个人信息管理

##### 返回个人信息

```java
/**
 * 返回个人信息
 *
 * @return data：{"user": user, "ACAMEDIES": ACAMEDIES}
 *
 * user包含属性很多..这些数据建议在首页直接展示出来
 * ACAMEDIES为可供选择的院系
 */
 @GetMapping("/customer/personal")
```

##### 修改头像

```java
/**
 * 上传头像到本地 获取path返回
 *
 * @param file 被上传的文件
 * @return data：{"avatar": avatar}
 *
 * avatar为新头像的base64编码
 */
@PostMapping("/customer/uploadAvatar")
```

##### 个人信息修改

```java
 /**
 * 修改发送过来的信息
 * 最后检查昵称是否已经存在，存在的话就改其他的，返回前端昵称已存在。
 * 最后检查密码是否符合格式规范。
 *
 * @param user1 更改个人信息后前端封装起来
 * @return data：{"token": token}
 *
 * token为更新信息后返回的新token令牌
 */
@PostMapping("/customer/modifyAll")
```

#### 个人问题管理

##### 发布问题

```java
/**
 * 新增问题 初始化各部分属性
 *
 * @param question 前端封装的Question对象
 * @return data：{"question": question}
 *
 * question为新增的问题
 */
@PostMapping("/customer/question/post")
```

##### 删除问题

```java
/**
 * @param questionId 问题Id
 * @return data：{null}
 */
@GetMapping("/customer/question/{questionId}/delete")
```

##### 搜索问题

```java
/**
 * 个人主页搜索 根据标签+关键字查询 返回个人发出的问题
 *
 * @param queryMap : {"tagIds": tagIds, "query" : query}
 * @return data：{"pages": pages}
 *
 * query为关键字
 */
@PostMapping("/customer/searchQuestions")
```

#### 个人博客管理

##### 返回要编辑的博客

```java
/**
 * 编辑博客 返回之前的博客到前端
 *
 * @param blogId 被编辑的博客Id
 * @return data：{"template": t, "tags", tags}
 * t为博客，tags为标签树
 */
@GetMapping("/customer/blog/{blogId}/edit")
```

##### 发布/编辑博客

```java
/**
 * 更新或添加博客
 *
 * @param blog 前端封装的博客
 * @return data：{"blog", blog}
 * blog是保存了的博客
 */
@PostMapping("/customer/blog/post")
```

##### 删除博客

```java
/**
 * 删除博客
 *
 * @param blogId 被删除的博客Id
 * @return data：{null}
 */
@GetMapping("/customer/blog/{blogId}/delete")
```

##### 搜索博客

```java
/**
 * 类上
 */
@PostMapping("/customer/searchQuestions")
```

#### 关注管理

```java
/**
 * 展示自己关注的人和自己的粉丝
 *
 * @result data: {"followedUsers" : followedUsers, "followingUsers", followingUsers}
 */
@GetMapping("/customer/followInfo")

```

### 常规

#### 评论

##### 返回某问题下的所有评论

```java
/**
 * 展示博客下的所有评论
 *
 * @param questionId 评论在哪个问题下 对应的问题Id
 * @return data：{"comments1": isAnswerComment, "comments2": notAnswerComment}
 * 
 * isAnswerComment为正经回答的评论 notAnswerComment是灌水的
 */
@GetMapping("/question/{questionId}/comments")

```

##### 返回某博客下的所有评论

```java
/**
 * 展示博客下所有评论 同上
 */
@GetMapping("/blog/{blogId}/comments")
```

##### 发布问题下评论

(注意 如果是直接评论帖子 parentCommentId为-1)

```java
/**
 * 新增评论
 * 提交表单后 到这里 然后得到id 然后刷新评论
 *
 * @param questionId对应的问题Id
 * @param comment 前端封装的Comment对象
 * @return data：{”comments": comments}
 * comments为发布后博客的新评论集合
 *
 * comment为保存了的Comment对象
 */
@PostMapping("/question/{questionId}/comment/post")
```

##### 发布博客下评论

```java
/**
 * 类上
 */
@PostMapping("/blog/{blogId}/comment/post")
```

##### 发布评论的子评论

与上面的用一个API，具体哪个看是博客下的还是问题下的，将parentCommentId0换成实际的Id，而不是-1了

##### 删除问题下评论

```java
/**
* 删除问题的评论
*
* @param questionId 对应问题Id
* @param commentId 被删除的评论对应的Id
* @param request 获取要执行删除操作的用户id
* @return data：{"comments": comments}
* comments为删除后剩余评论
*/
@GetMapping("/question/{questionId}/comment/{commentId}/delete)
```

##### 删除博客下评论

```java
/**
 * 类上
 */
@GetMapping("/blog/{blogId}/comment/{commentId}/delete")
```

##### 删除评论的子评论

类上

#### 点赞/踩

##### 点赞/取消赞问题

```java
/**
 * 点赞/取消赞问题
 *
 * @param questionId 问题Id
 * @return data：{"likesNum": likesNum, "disLikesNum": disLikesNum, "approved": approved}
 * likesNum为操作后的赞数, disLikesNum为操作后的踩数,approved为我现在点没点赞
 */
@GetMapping("/question/{questionId}/approve")
```

##### 点赞/取消赞博客

```java
/**
 * 类上
 */
@GetMapping("/blog/{blogId}/approve")
```

##### 点踩/取消踩问题

```java
/**
 * 点踩/取消踩问题
 *
 * @param request获取执行踩动作的用户id
 * @param questionId 问题Id
 * @return data：{"likesNum": likesNum, "disLikesNum": disLikesNum, "disapproved": disApproved}
 * likesNum为操作后的赞数, disLikesNum为操作后的踩数, disapproved为我现在踩没踩 
 */
@GetMapping("/question/{questionId}/disapprove")
```

##### 点踩/取消踩博客

```java
/**
 * 类上
 */
@GetMapping("/blog/{blogId}/disapprove")
```

##### 点赞/取消赞评论

```java
/**
 * 给评论点赞
 *
 * @param commentId 被点赞的评论Id
 * 类上
 */
@GetMapping("/comment/{commentId}/approve")
```

##### 点踩/取消踩评论

```java
/**
 * 点踩 到标准就隐藏
 *
 * @param commentId 评论Id
 * 类上
 */
@GetMapping("/comment/{commentId}/disapprove")
```

#### 关注

##### 取关/关注

```java
/**
 * 取关/关注
 * 
 * @param followedUserId 被关注的用户Id
 * @return data：{"myFollowingNum": myFollowingNum, "hisFollowedNum": hisFollowedNum, "following" : following}
 * myFollowingNum是我现在关注了多少人，hisFollowedNum是他现在被多少人关注，following是我现在有没有
 * 关注他
 */
@GetMapping("/follow/{followedUserId}")
```

#### 问题

##### 返回推荐问题、全部问题

```java
/**
 *
 * @return data：{"pages": pages, "impacts": impacts}
 *
 * pages为所有问题的分页，以Page封装；impacts为推荐问题的List集合；
 */
@GetMapping("/listQuestions")
```

##### 搜索问题

```java
/**
 * 按输入搜索问题的标题/内容/概述
 *
 * @param query : {"query" : query}
 * @return data：{"pages": pages, "query": query}
 *
 * pages为搜索结果的分页；query为前端发送过来的查询条件，建议搜索后仍保留这个query在搜索框；
 */
@PostMapping("/searchQuestions")
```

##### 问题内容展示

```java
/**
 * 问题内容展示
 *
 * @param questionId 问题Id
 * @return data：{"template", question}
 *
 * template为问题内容
 */
@GetMapping("/question/{questionId}")
```

#### 博客

##### 返回推荐博客、全部博客

```java
/**
 * 类上
 */
@GetMapping("/listBlogs")
```

##### 搜索博客

```java
/**
 * 类上
 */
@PostMapping("/searchBlogs")
```

##### 博客内容展示

```java
/**
 * 类上
 */
@GetMapping("/blog/{blogId}")
```

#### 登录 注册

##### 注册

```java
/**
 * 注册
 *
 * @param user 前端封装好的User对象
 * @return data：{"user": user}
 * 
 * user为前端之前发送过来的User对象，不过将password去掉了，需要用户重新输入
 */
@PostMapping("/register")

```

##### 登录

```java
/**
 * 登录
 *
 * @param user 前端封装的User对象
 * @return data：{"token", token, "avatar": avatar}
 *
 * token为登录返回前端的令牌, avatar是返回的头像的base64编码
 */
@PostMapping("/login")
```

#### 排行榜

##### 返回top10用户

```java
/**
 * @return data：{"users": users, "myRank", myRank}
 *
 * users为按贡献值排序的用户集合 选择前十 这几个users对象中只包含以下数据：nickname，donation，avatar、Id、followedUserNum、followingUserNum
 * myRank为当前用户的排名
 */
@GetMapping("/rank")
```

#### 访问主页

访问用户的主页

```java
/**
 * @param userId 目标用户Id
 * @return data：{"user": user}
 * 
 * user为用户主页信息
 */
@GetMapping("visit/{userId}")
```

#### 标签页

##### 返回所有标签

```java
/**
 * @return data：{"tags": tags}
 *
 * tags为所有标签，是树形结构
 */
@GetMapping("/tags/")
```

##### 按标签查询问题

```java
/**
 * 按标签查询
 *
 * @param tagIds 以,分割的标签Id
 * @return data：{"pages", pages}
 *
 * pages为查询结果的问题分页
 */
@PostMapping("/searchQuestions")
```

##### 按标签查询博客

```java
/** * 类上 */@PostMapping("/searchBlogs")
```

#### 文献资料库

##### 上传文件

```Java
/**  * 文件上传 * @param uploadFile 用户需要上传的文件 * @param fileTagName 前端传入对应标签名字 */@PostMapping("files/upload")
```

#### 文献资料库

##### 返回所有标签

```java
/**
 * 返回所有标签
 * @return 所有文件标签
 */
@GetMapping("/getAllTags")
```

##### 查询关键词相关文件

```java
/**
 * 查询关键词相关文件
 * @param query 用户想要查询的文件关键词
 * @param pageIndex 前端传入需要的页数
 * @return 查询结果
 */
@GetMapping("/find")
```



##### 上传文件

```Java
/** 
 * 文件上传
 * @param uploadFile 用户需要上传的文件
 * @param fileTagName 前端传入对应标签名字
 */
@PostMapping("files/upload")
```

##### 下载文件

```java
/**
 * @param fileId 传入文件Id来下载
 */
@GetMapping("files/download")
```

##### 查询文件

```Java
/**
 * 查询关键词相关文件
 * @param query 用户想要查询的文件关键词
 * @return page分页的文件结果
 */
@GetMapping("/find")
```


