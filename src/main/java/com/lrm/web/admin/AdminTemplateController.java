package com.lrm.web.admin;

import com.lrm.exception.FailedOperationException;
import com.lrm.exception.IllegalParameterException;
import com.lrm.exception.NotFoundException;
import com.lrm.po.Blog;
import com.lrm.po.Question;
import com.lrm.service.BlogServiceImpl;
import com.lrm.service.QuestionServiceImpl;
import com.lrm.service.TagServiceImpl;
import com.lrm.vo.Result;
import com.lrm.web.customer.CustomerTemplateController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员控制问题和博客
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@RequestMapping("/admin")
@RestController
public class AdminTemplateController {
    @Autowired
    private TagServiceImpl tagServiceImpl;

    @Autowired
    private QuestionServiceImpl questionServiceImpl;

    @Autowired
    private BlogServiceImpl blogServiceImpl;

    @Autowired
    CustomerTemplateController customerTemplateController;

    /**
     * 管理员编辑问题 返回之前的问题到前端
     *
     * @param questionId 被编辑的问题Id
     * @return 该问题对象和所有标签
     */
    @GetMapping("/question/{questionId}/edit")
    public Result editQuestion(@PathVariable Long questionId) {
        Question question = questionServiceImpl.getById(questionId);
        if (question == null) {
            throw new NotFoundException("未查询到该问题");
        }
        return customerTemplateController.editTemplate(question);
    }


    /**
     * 管理员编辑完问题发布
     *
     * @param question      被编辑的对象
     * @param bindingResult 属性校验
     * @return 成功/失败
     */
    @PostMapping("/question/post")
    public Result post(@RequestBody @Valid Question question, BindingResult bindingResult) {
        Map<String, Object> hashMap = new HashMap<>(1);

        //后端检验valid
        if (bindingResult.hasErrors()) {
            throw new IllegalParameterException(IllegalParameterException.getMessage(bindingResult));
        }

        if (question.getId() == null) {
            throw new NotFoundException("未查询到该问题");
        }

        //令前端只传回tagIds而不是tag对象 将它转换为List<Tag> 在service层找到对应的Tag保存到数据库
        question.setTags(tagServiceImpl.listTag(question.getTagIds()));
        Question q = questionServiceImpl.update(question);

        if (q == null) {
            throw new FailedOperationException("修改失败");
        } else {
            hashMap.put("question", question);
            return new Result(hashMap, "修改成功");
        }
    }

    /**
     * 管理页根据标题、标签、用户昵称搜索
     *
     * @param pageable 分页对象
     * @param question 对于Question的查询条件
     * @return 查询结果
     */
    @PostMapping("searchQuestions")
    public Result searchQuestions(@PageableDefault(size = 1000, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                                 @RequestBody Question question) {
        return customerTemplateController.searchTemplate(pageable, question, questionServiceImpl, question.getNickname());
    }

    /**
     * 管理页根据标题、标签、用户昵称搜索 前端传入Blog对象、nickname
     *
     * @param pageable 分页对象
     * @param blog 对于Blog的查询条件
     * @return 查询结果
     */
    @PostMapping("searchBlogs")
    public Result searchBlogs(@PageableDefault(size = 1000, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                             @RequestBody Blog blog) {
        return customerTemplateController.searchTemplate(pageable, blog, blogServiceImpl, blog.getNickname());
    }
}
