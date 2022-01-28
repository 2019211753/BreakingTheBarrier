package com.lrm.web.customer;

import com.lrm.exception.FailedOperationException;
import com.lrm.exception.IllegalParameterException;
import com.lrm.exception.NoPermissionException;
import com.lrm.exception.NotFoundException;
import com.lrm.po.*;
import com.lrm.service.*;
import com.lrm.util.TokenInfo;
import com.lrm.vo.BlogShow;
import com.lrm.vo.Magic;
import com.lrm.vo.QuestionShow;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人问题管理
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@RequestMapping("/customer")
@RestController
public class CustomerTemplateController {
    @Autowired
    private TagServiceImpl tagServiceImpl;

    @Autowired
    private QuestionServiceImpl questionServiceImpl;

    @Autowired
    private BlogServiceImpl blogServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;


    /**
     * 新增问题 初始化各部分属性
     *
     * @param request       获得当前用户id
     * @param question      前端封装的question对象
     * @param bindingResult 配合@Valid检测是否为空
     * @return 报错信息/成功信息
     */
    @PostMapping("/question/post")
    public Result addQuestion(@RequestBody @Valid Question question, BindingResult bindingResult, HttpServletRequest request)  {
        Long userId = TokenInfo.getCustomUserId(request);
        User user = userServiceImpl.getUser(userId);

        if (user.getCanSpeak()) {
            if (bindingResult.hasErrors()) {
                throw new IllegalParameterException(IllegalParameterException.getMessage(bindingResult));
            }

            Question q;
            if (question.getId() == null) {
                q = questionServiceImpl.save(question, user);
            } else {
                throw new FailedOperationException("该问题已存在");
            }

            if (q != null) {
                return new Result(null, "发布成功");
            } else {
                throw new FailedOperationException("发布失败");
            }
        } else {
            throw new NoPermissionException("你无权发布问题");
        }
    }

    /**
     * 更新或添加博客
     *
     * @param blog 前端封装的博客
     * @param bindingResult 校验器
     * @param request 获取当前userId
     * @return 保存了的博客
     */
    @PostMapping("/blog/post")
    public Result addOrUpdateBlog(@RequestBody @Valid Blog blog, BindingResult bindingResult, HttpServletRequest request) {
        Long userId = TokenInfo.getCustomUserId(request);
        User user = userServiceImpl.getUser(userId);

        if (user.getCanSpeak()) {
            //后端检验valid 如果校验失败 返回input页面
            if (bindingResult.hasErrors()) {
                throw new IllegalParameterException(IllegalParameterException.getMessage(bindingResult));
            }

            if (blog.getOrigin()) {
                blog.setTransferStatement(null);
            } else {
                if (blog.getTransferStatement() == null || "".equals(blog.getTransferStatement())) {
                    throw new IllegalParameterException("请输入原文来源");
                }
            }
            blog.setUser(user);

            Blog b;
            if (blog.getId() == null) {
                b = blogServiceImpl.save(blog, user);
            } else {
                b = blogServiceImpl.update(blog);
            }
            if (b == null) {
                throw new FailedOperationException("发布失败");
            } else {
                return new Result(null, "发布成功");
            }
        } else {
            throw new NoPermissionException("你无权发布博客");
        }

    }


    /**
     * 删除问题
     *
     * @param request    获得当前用户id
     * @param questionId 问题Id
     * @return 报错信息/成功信息
     */
    @GetMapping("/question/{questionId}/delete")
    public Result deleteQuestion(@PathVariable Long questionId, HttpServletRequest request) {
        Long userId = TokenInfo.getCustomUserId(request);
        Boolean admin = TokenInfo.isAdmin(request);

        Question question = questionServiceImpl.getById(questionId);
        if (question == null) {
            throw new NotFoundException("未查询到该问题");
        }
        if ((!question.getUser().getId().equals(userId)) && (!admin)) {
            throw new NoPermissionException("你无权删除该问题");
        }

        return deleteTemplate(question, questionServiceImpl);
    }

    /**
     * 删除博客
     *
     * @param request    获得当前用户id
     * @return 报错信息/成功信息
     */
    @GetMapping("/blog/{blogId}/delete")
    public Result deleteBlog(@PathVariable Long blogId, HttpServletRequest request) {
        Long userId = TokenInfo.getCustomUserId(request);
        Boolean admin = TokenInfo.isAdmin(request);

        Blog blog = blogServiceImpl.getById(blogId);

        if (blog == null) {
            throw new NotFoundException("未查询到该博客");
        }
        if ((!blog.getUser().getId().equals(userId)) && (!admin)) {
            throw new NoPermissionException("你无权删除该博客");
        }

        return deleteTemplate(blog, blogServiceImpl);
    }

    public <T extends Template> Result deleteTemplate(T t, TemplateServiceImpl<T> templateServiceImpl) {

        Long id = t.getId();

        templateServiceImpl.deleteById(id);

        t = templateServiceImpl.getById(id);
        if (t != null) {
            throw new FailedOperationException("删除失败");
        } else {
            return new Result(null, "删除成功");
        }
    }


    /**
     * 编辑博客 返回之前的博客到前端
     *
     * @param blogId 被编辑的博客Id
     * @return 该博客对象和所有标签
     */
    @GetMapping("/blog/{blogId}/edit")
    public Result editBlog(@PathVariable Long blogId, HttpServletRequest request) {
        Long userId = TokenInfo.getCustomUserId(request);
        Blog blog = blogServiceImpl.getById(blogId);
        if (blog == null) {
            throw new NotFoundException("未查询到该博客");
        }
        if (!blog.getUser().getId().equals(userId)) {
            throw new NoPermissionException("你无权获取该博客");
        }
        Map<String, Object> hashMap = new HashMap<>(2);
        blog = editTemplate(blog);
        List<Tag> tags = tagServiceImpl.listTagTop();
        hashMap.put("template", new BlogShow(blog));
        hashMap.put("tags", tags);
        return new Result(hashMap, "");
    }

    /**
     * @param t Blog/Question
     * @param <T> t的类型
     * @return 返回前端的博客/问题
     */
    public <T extends Template> T editTemplate(T t) {
        t.init();
        t.setDetails(t.getContent());
        return t;
    }


    /**
     * 个人主页搜索 根据标题+标签 返回个人发出的问题
     * 跟上面那个get方法的不同就是 一个question是空的 一个不是空的
     *
     * @param request  获得当前用户id
     * @param pageable 分页标准
     * @param query 查询条件
     * @return 查询所得问题分页
     */
    @PostMapping("/searchQuestions")
    public Result searchQuestions(@PageableDefault(size = Magic.SEARCH_PAGE_SIZE, sort = {"newCommentedTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                                  @RequestBody Map<String, String> query, HttpServletRequest request) {

        //得到当前用户的昵称
        String nickname = TokenInfo.getCustomNickname(request);
        query.put("nickname", nickname);

        Map<String, Object> hashMap = new HashMap<>(1);

        Page<Question> ts = searchTemplate(pageable, query, questionServiceImpl);
        hashMap.put("pages", ts.map(QuestionShow::new));
        return new Result(hashMap, "");    }

    /**
     * 个人主页搜索 根据标题+标签 返回个人发出的博客
     *
     * @param request  获得当前用户id
     * @param pageable 分页标准
     * @param query 查询条件
     * @return 查询所得博客分页
     */
    @PostMapping("/searchBlogs")
    public Result searchBlogs(@PageableDefault(size = Magic.SEARCH_PAGE_SIZE, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                              @RequestBody Map<String, String> query, HttpServletRequest request) {

        //得到当前用户的昵称
        String nickname = TokenInfo.getCustomNickname(request);
        query.put("nickname", nickname);

        Map<String, Object> hashMap = new HashMap<>(1);

        Page<Blog> ts = searchTemplate(pageable, query, blogServiceImpl);
        hashMap.put("pages", ts.map(BlogShow::new));
        return new Result(hashMap, "");    }

    /**
     * @param pageable 分页对象
     * @param query    查询条件
     * @param templateServiceImpl 对应的service层
     * @param <T> t的类型
     * @return 搜索结果
     */
    public <T extends Template> Page<T> searchTemplate(Pageable pageable, Map<String, String> query, TemplateServiceImpl<T> templateServiceImpl) {
        Page<T> ts = templateServiceImpl.listByQueryAndTagIdsAndNickname(pageable, query);

        for (T t0 : ts.getContent()) {
            //得到发布问题的人
            User postUser = t0.getUser();
            t0.setAvatar(postUser.getAvatar());
            t0.setNickname(postUser.getNickname());
        }
        return ts;
    }
}
