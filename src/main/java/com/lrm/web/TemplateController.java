package com.lrm.web;

import com.lrm.exception.IllegalParameterException;
import com.lrm.exception.NotFoundException;
import com.lrm.po.*;
import com.lrm.service.*;
import com.lrm.util.FileTypeUtils;
import com.lrm.util.FileUtils;
import com.lrm.util.OSSUtils;
import com.lrm.util.TokenInfo;
import com.lrm.vo.BlogShow;
import com.lrm.vo.Magic;
import com.lrm.vo.QuestionShow;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 乱七八糟的
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@RestController
public class TemplateController {

    @Autowired
    private QuestionServiceImpl questionServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private LikesServiceImpl likesServiceImp;

    @Autowired
    private DisLikesServiceImpl dislikesServiceImp;

    @Autowired
    private FavoriteServiceImpl favoriteServiceImpl;

    @Autowired
    private BlogServiceImpl blogServiceImpl;

    @Value("${oss.endpoint_content}")
    private String endpoint;

    @Value("${oss.accessKeyId_content}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret_content}")
    private String accessKeySecret;

    @Value("${oss.bucketName_content}")
    private String bucketName;

    @Value("${oss.urlPrefix_content}")
    private String urlPrefix;

    /**
     * 返回主页问题
     *
     * @param pageable 分页
     * @return 返回推荐问题、全部问题
     */
    @GetMapping("/listQuestions")
    public Result listQuestions(@PageableDefault(size = Magic.INDEX_PAGE_SIZE, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Map<String, Object> hashMap = new HashMap<>(2);
        Page<Question> pages = listTemplates(questionServiceImpl, pageable);
        hashMap.put("pages", pages.map(QuestionShow::new));
        hashMap.put("impacts", QuestionShow.getCommentsShow(questionServiceImpl.listImpactTop(Magic.RECOMMENDED_SIZE)));
        return new Result(hashMap, "");    }

    /**
     * 返回主页问题
     *
     * @param pageable 分页
     * @return 返回推荐问题、全部问题
     */
    @GetMapping("/listBlogs")
    public Result listBlogs(@PageableDefault(size = Magic.INDEX_PAGE_SIZE, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Map<String, Object> hashMap = new HashMap<>(2);
        Page<Blog> pages = listTemplates(blogServiceImpl, pageable);
        hashMap.put("pages", pages.map(BlogShow::new));
        hashMap.put("impacts", BlogShow.getCommentsShow(blogServiceImpl.listImpactTop(Magic.RECOMMENDED_SIZE)));
        return new Result(hashMap, "");
    }

    <T extends Template> Page<T> listTemplates(TemplateServiceImpl<T> templateServiceImpl, Pageable pageable) {

        Page<T> pages = templateServiceImpl.listAll(pageable);

        for (T t : pages) {

            //得到发布问题的人
            User postUser = t.getUser();
            t.setAvatar(postUser.getAvatar());
            t.setNickname(postUser.getNickname());
        }
       return pages;
    }


    /**
     * 按输入搜索标题/内容
     *
     * @param pageable 分页
     * @param query    查询条件
     * @return 查询结果、查询条件
     */
    @PostMapping("/searchQuestions")
    public Result searchQuestions(@PageableDefault(size = Magic.SEARCH_PAGE_SIZE, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                              @RequestBody Map<String, String> query) {
        Map<String, Object> hashMap = new HashMap<>(2);

        Page<Question> pages =  searchTemplates(questionServiceImpl, query.get("query"), pageable);
        hashMap.put("pages", pages.map(QuestionShow::new));
        //还要传回 保证在新的查询页面 查询框中也有自己之前查询的条件的内容
        hashMap.put("query", query);
        return new Result(hashMap, "");
    }

    /**
     * 按输入搜索标题/内容
     *
     * @param pageable 分页
     * @param query    查询条件
     * @return 查询结果、查询条件
     */
    @PostMapping("/searchBlogs")
    public Result searchBlogs(@PageableDefault(size = Magic.SEARCH_PAGE_SIZE, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                              @RequestBody Map<String, String> query) {

        Map<String, Object> hashMap = new HashMap<>(2);

        Page<Blog> pages =  searchTemplates(blogServiceImpl, query.get("query"), pageable);
        hashMap.put("pages", pages.map(BlogShow::new));
        //还要传回 保证在新的查询页面 查询框中也有自己之前查询的条件的内容
        hashMap.put("query", query);
        return new Result(hashMap, "");
    }

    <T extends Template> Page<T> searchTemplates(TemplateServiceImpl<T> templateServiceImpl, String query, Pageable pageable) {
        Map<String, Object> hashMap = new HashMap<>(2);

        //mysql语句 模糊查询的格式 jpa不会帮处理string前后有没有%的
        Page<T> pages = templateServiceImpl.listByQuery("%" + query + "%", pageable);
        for (T t0 : pages.getContent()) {
            //得到发布问题的人
            User postUser = t0.getUser();

            t0.setAvatar(postUser.getAvatar());
            t0.setNickname(postUser.getNickname());
        }
        return pages;
    }

    /**
     * 问题内容展示
     *
     * @param questionId 问题Id
     * @return 问题的内容
     */
    @GetMapping("/question/{questionId}")
    public Result getQuestion(@PathVariable Long questionId, HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(2);
        Long userId = TokenInfo.getCustomUserId(request);

        Question backQuestion = questionServiceImpl.getById(questionId);
        if (backQuestion == null) {
            throw new NotFoundException("未查询到该博客");
        }
        //返回前端的问题
        Question frontQuestion = new Question();

        frontQuestion = getTemplate(userId, backQuestion, frontQuestion, questionServiceImpl);
        hashMap.put("template", new QuestionShow(frontQuestion));

        User receiveUser = frontQuestion.getUser();
        User postUser = userServiceImpl.getUser(userId);
        if (receiveUser.getFollowedUsers().contains(postUser) &&
                postUser.getFollowingUsers().contains(receiveUser)) {
            hashMap.put("following", true);
        } else {
            hashMap.put("following", false);
        }

        return new Result(hashMap, "");
    }

    /**
     * 博客内容展示
     *
     * @param blogId 博客Id
     * @return 博客的内容
     */
    @GetMapping("/blog/{blogId}")
    public Result getBlog(@PathVariable Long blogId, HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(2);
        Long userId = TokenInfo.getCustomUserId(request);

        Blog backBlog = blogServiceImpl.getById(blogId);
        if (backBlog == null) {
            throw new NotFoundException("未查询到该博客");
        }
        //返回前端的博客
        Blog frontBlog = new Blog();

        frontBlog = getTemplate(userId, backBlog, frontBlog, blogServiceImpl);
        hashMap.put("template", new BlogShow(frontBlog));

        User receiveUser = frontBlog.getUser();
        User postUser = userServiceImpl.getUser(userId);
        if (receiveUser.getFollowedUsers().contains(postUser) &&
                postUser.getFollowingUsers().contains(receiveUser)) {
            hashMap.put("following", true);
        } else {
            hashMap.put("following", false);
        }

        return new Result(hashMap, "");
    }

    <T extends Template> T getTemplate(Long userId, T backT, T frontT, TemplateServiceImpl<T> templateServiceImpl) {
        frontT = templateServiceImpl.getAndConvert(backT.getId(), frontT);

        frontT.setApproved(likesServiceImp.get(userServiceImpl.getUser(userId), backT) != null);
        frontT.setDisapproved(dislikesServiceImp.get(userServiceImpl.getUser(userId), backT) != null);
        frontT.setCollected(false);

        StringBuilder favoriteIds = new StringBuilder();
        Favorite favorite0;
        Iterator<Favorite> it = favoriteServiceImpl.getFavoritesByUserId(userId).iterator();
        if (it.hasNext()) {
            favorite0 = it.next();
            if (favorite0.getFavoriteQuestions().contains(backT) || favorite0.getFavoriteBlogs().contains(backT)) {
                favoriteIds.append(favorite0.getId());
                frontT.setCollected(true);
            }
        }

        while (it.hasNext()) {
            favorite0 = it.next();
            if (favorite0.getFavoriteQuestions().contains(backT) || favorite0.getFavoriteBlogs().contains(backT)) {
                favoriteIds.append(",").append(favorite0.getId());
                frontT.setCollected(true);
            }
        }

        frontT.setFavoriteIds(favoriteIds.toString());
        frontT.setAvatar(backT.getUser().getAvatar());
        frontT.setNickname(backT.getUser().getNickname());

        return frontT;
    }

    /**
     * 上传文件到oss 获取url返回
     *
     * @param req  获取当前用户id
     * @param files 被上传的文件
     * @return fileUrl 文件的url
     */
    @PostMapping("/uploadPictures")
    public Result uploadPictures(@RequestBody  MultipartFile[] files, HttpServletRequest req) {
        Map<String, Object> hashMap = new HashMap<>(1);
        Long userId = TokenInfo.getCustomUserId(req);

        List<String> urls = new ArrayList<>(files.length);
        int num = 0;
        for (MultipartFile file : files) {
            num += 1;
            if (file.getOriginalFilename() == null) {
                throw new IllegalParameterException("请为文件命名");
            }

            //限制文件大小
            long size = file.getSize();
            String suffix = FileTypeUtils.getFileType(file);
            //如果是图片
            if ("PNG".equalsIgnoreCase(suffix) ||
                "JPG".equalsIgnoreCase(suffix) ||
                "JPEG".equalsIgnoreCase(suffix) ||
                "BMP".equalsIgnoreCase(suffix) ||
                "GIF".equalsIgnoreCase(suffix)) {

                if (size / 1024 > Magic.MAX_UPLOAD_PICTURE_SIZE_BYTES) {
                    throw new IllegalParameterException("第" + num + "个文件大小为：" + size / 1024 + "KB，超过了" + Magic.MAX_UPLOAD_PICTURE_SIZE_BYTES + "KB");
                }
            //如果是文件
            } else if ("doc".equalsIgnoreCase(suffix) ||
                       "pdf".equalsIgnoreCase(suffix) ||
                       "docx".equalsIgnoreCase(suffix) ||
                        //txt没有魔数
                       "txt".equalsIgnoreCase(FileUtils.getSuffix(file.getOriginalFilename()))
            ) {
                if (size / 1048576 > Magic.MAX_UPLOAD_DOCUMENT_SIZE_MBYTES) {
                    throw new IllegalParameterException("第" + num + "个文件大小为：" + size / 1048576 + "MB，超过了" + Magic.MAX_UPLOAD_DOCUMENT_SIZE_MBYTES + "MB");
                }
            }

            String catalog = userId + "/content/";
            //再创建目录
            String fileName = FileUtils.getFileName(file.getOriginalFilename());
            OSSUtils.uploadFile(file, endpoint, accessKeyId, accessKeySecret, bucketName,
                    catalog, fileName);

            String fileUrl = "https://" + urlPrefix + "/" + catalog + fileName;
            urls.add(fileUrl);
        }

        hashMap.put("fileUrls", urls);
        return new Result(hashMap, "上传成功");
    }

}
