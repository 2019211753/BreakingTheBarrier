package com.lrm.web;

import com.lrm.exception.NotFoundException;
import com.lrm.po.*;
import com.lrm.service.*;
import com.lrm.util.FileUtils;
import com.lrm.util.TokenInfo;
import com.lrm.vo.Magic;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

    /**
     * 返回主页问题
     *
     * @param pageable 分页
     * @return 返回推荐问题、全部问题
     */
    @GetMapping("/listQuestions")
    public Result listQuestions(@PageableDefault(size = Magic.INDEX_PAGE_SIZE, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return listTemplates(questionServiceImpl, pageable);
    }

    /**
     * 返回主页问题
     *
     * @param pageable 分页
     * @return 返回推荐问题、全部问题
     */
    @GetMapping("/listBlogs")
    public Result listBlogs(@PageableDefault(size = Magic.INDEX_PAGE_SIZE, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return listTemplates(blogServiceImpl, pageable);
    }

    <T extends Template> Result listTemplates(TemplateServiceImpl<T> templateServiceImpl, Pageable pageable) {
        Map<String, Object> hashMap = new HashMap<>(3);

        Page<T> pages = templateServiceImpl.listAll(pageable);

        for (T t : pages) {

            //得到发布问题的人
            User postUser = t.getUser();
            t.setAvatar(FileUtils.convertAvatar(postUser.getAvatar()));
            t.setNickname(postUser.getNickname());
        }
        hashMap.put("pages", pages);
        hashMap.put("impacts", templateServiceImpl.listImpactTop(Magic.RECOMMENDED_SIZE));
        return new Result(hashMap, "");
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

        return searchTemplates(questionServiceImpl, query.get("query"), pageable);
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

        return searchTemplates(blogServiceImpl, query.get("query"), pageable);
    }

    <T extends Template> Result searchTemplates(TemplateServiceImpl<T> templateServiceImpl, String query, Pageable pageable) {
        Map<String, Object> hashMap = new HashMap<>(2);

        //mysql语句 模糊查询的格式 jpa不会帮处理string前后有没有%的
        Page<T> pages = templateServiceImpl.listByQuery("%" + query + "%", pageable);
        for (T t0 : pages.getContent()) {
            //得到发布问题的人
            User postUser = t0.getUser();

            t0.setAvatar(FileUtils.convertAvatar(postUser.getAvatar()));
            t0.setNickname(postUser.getNickname());
        }

        hashMap.put("pages", pages);

        //还要传回 保证在新的查询页面 查询框中也有自己之前查询的条件的内容
        hashMap.put("query", query);
        return new Result(hashMap, "");
    }

    /**
     * 问题内容展示
     *
     * @param questionId 问题Id
     * @return 问题的内容
     */
    @GetMapping("/question/{questionId}")
    public Result getQuestion(@PathVariable Long questionId, HttpServletRequest request) {
        Question backQuestion = questionServiceImpl.getById(questionId);
        if (backQuestion == null) {
            throw new NotFoundException("未查询到该问题");
        }
        //返回前端的问题
        Question frontQuestion = new Question();

        return getTemplate(request, backQuestion, frontQuestion, questionServiceImpl);
    }

    /**
     * 博客内容展示
     *
     * @param blogId 博客Id
     * @return 博客的内容
     */
    @GetMapping("/blog/{blogId}")
    public Result getBlog(@PathVariable Long blogId, HttpServletRequest request) {
        Blog backBlog = blogServiceImpl.getById(blogId);
        if (backBlog == null) {
            throw new NotFoundException("未查询到该博客");
        }
        //返回前端的问题
        Blog frontBlog = new Blog();

        return getTemplate(request, backBlog, frontBlog, blogServiceImpl);
    }

    <T extends Template> Result getTemplate(HttpServletRequest request, T backT, T frontT, TemplateServiceImpl<T> templateServiceImpl) {
        Map<String, Object> hashMap = new HashMap<>(1);
        Long userId = TokenInfo.getCustomUserId(request);

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
        frontT.setAvatar(FileUtils.convertAvatar(backT.getUser().getAvatar()));
        frontT.setNickname(backT.getUser().getNickname());

        hashMap.put("template", frontT);

        User receiveUser = frontT.getUser();
        User postUser = userServiceImpl.getUser(userId);
        if (receiveUser.getFollowedUsers().contains(postUser) &&
                postUser.getFollowingUsers().contains(receiveUser)) {
            hashMap.put("following", true);
        } else {
            hashMap.put("following", false);
        }

        return new Result(hashMap, "");
    }
}
