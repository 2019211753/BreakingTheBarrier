package com.lrm.web;

import com.lrm.exception.NotFoundException;
import com.lrm.po.*;
import com.lrm.service.*;
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
    public Result listQuestions(@PageableDefault(size = 7, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return listTemplates(questionServiceImpl, pageable);
    }

    /**
     * 返回主页问题
     *
     * @param pageable 分页
     * @return 返回推荐问题、全部问题
     */
    @GetMapping("/listBlogs")
    public Result listBlogs(@PageableDefault(size = 7, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return listTemplates(blogServiceImpl, pageable);
    }

    <T extends Template> Result listTemplates(TemplateServiceImpl<T> templateServiceImpl, Pageable pageable) {
        Map<String, Object> hashMap = new HashMap<>(3);

        Page<T> pages = templateServiceImpl.listAll(pageable);

        for (T t : pages) {

            //得到发布问题的人
            User postUser = t.getUser();
            //这里到底要不要用计算力代替空间还要考虑
            t.setAvatar(postUser.getAvatar());
            t.setNickname(postUser.getNickname());
        }
        hashMap.put("pages", pages);
        hashMap.put("impacts", templateServiceImpl.listImpactTop(Magic.RECOMMENDED_QUESTIONS_SIZE));
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
    public Result searchQuestions(@PageableDefault(size = 1000, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestBody String query) {

        return searchTemplates(questionServiceImpl, query, pageable);
    }

    /**
     * 按输入搜索标题/内容
     *
     * @param pageable 分页
     * @param query    查询条件
     * @return 查询结果、查询条件
     */
    @PostMapping("/searchBlogs")
    public Result searchBlogs(@PageableDefault(size = 1000, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                              @RequestBody String query) {

        return searchTemplates(blogServiceImpl, query, pageable);
    }

    <T extends Template> Result searchTemplates(TemplateServiceImpl<T> templateServiceImpl, String query, Pageable pageable) {
        Map<String, Object> hashMap = new HashMap<>(2);

        //mysql语句 模糊查询的格式 jpa不会帮处理string前后有没有%的
        Page<T> pages = templateServiceImpl.listByQuery("%" + query + "%", pageable);

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
        Question frontQuestion =new Question();

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
        Blog frontBlog =new Blog();

        return getTemplate(request, backBlog, frontBlog, blogServiceImpl);
    }

    <T extends Template> Result getTemplate(HttpServletRequest request, T backT, T frontT, TemplateServiceImpl<T> templateServiceImpl) {
        Map<String, Object> hashMap = new HashMap<>(1);
        Long userId = TokenInfo.getCustomUserId(request);

        frontT = templateServiceImpl.getAndConvert(backT.getId(), frontT);


        if (likesServiceImp.get(userServiceImpl.getUser(userId), backT) != null) {
            frontT.setApproved(true);
        } else {
            frontT.setApproved(false);
        }

        if (dislikesServiceImp.get(userServiceImpl.getUser(userId), backT) != null) {
            frontT.setDisapproved(true);
        } else {
            frontT.setDisapproved(false);
        }

        for (Favorite favorite : favoriteServiceImpl.getFavoritesByUserId(userId)) {
            if (favorite.getFavoriteQuestions().contains(backT) || favorite.getFavoriteBlogs().contains(backT)) {
                frontT.setCollected(true);
            } else {
                frontT.setCollected(false);
            }
        }

        hashMap.put("template", frontT);
        return new Result(hashMap, "");
    }
}
