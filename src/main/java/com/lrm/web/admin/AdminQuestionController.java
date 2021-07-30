package com.lrm.web.admin;

import com.lrm.exception.FailedOperationException;
import com.lrm.exception.IllegalParameterException;
import com.lrm.exception.NoPermissionException;
import com.lrm.exception.NotFoundException;
import com.lrm.po.Question;
import com.lrm.po.Tag;
import com.lrm.po.User;
import com.lrm.service.QuestionService;
import com.lrm.service.TagService;
import com.lrm.vo.QuestionQuery;
import com.lrm.vo.Result;
import com.lrm.web.customer.QuestionController;
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
 * 管理员控制问题
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@RequestMapping("/admin")
@RestController
public class AdminQuestionController {
    @Autowired
    private TagService tagService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    QuestionController questionController;

    /**
     * 管理员编辑问题 返回之前的问题到前端
     *
     * @param questionId 被编辑的问题Id
     * @return 该问题对象和所有标签
     */
    @GetMapping("/question/{questionId}/edit")
    public Result editInput(@PathVariable Long questionId) {
        Map<String, Object> hashMap = new HashMap<>(2);

        Question question = questionService.getQuestion(questionId);
        if (question == null) {
            throw new NotFoundException("未查询到该问题");
        }
        question.init();

        List<Tag> tags = tagService.listTagTop();

        hashMap.put("question", question);
        hashMap.put("tags", tags);

        return new Result(hashMap, "");
    }

    /**
     * 管理员编辑完问题发布
     *
     * @param question      被编辑的对象
     * @param bindingResult 属性校验
     * @return 成功/失败
     */
    @PostMapping("/questions")
    public Result post(@Valid Question question, BindingResult bindingResult) {
        Map<String, Object> hashMap = new HashMap<>(1);

        //后端检验valid
        if (bindingResult.hasErrors()) {
            throw new IllegalParameterException(IllegalParameterException.getMessage(bindingResult));
        }

        if (question.getId() == null) {
            throw new NotFoundException("未查询到该问题");
        }

        //令前端只传回tagIds而不是tag对象 将它转换为List<Tag> 在service层找到对应的Tag保存到数据库
        question.setTags(tagService.listTag(question.getTagIds()));
        Question q = questionService.updateQuestion(question);

        if (q == null) {
            throw new FailedOperationException("修改失败");
        } else {
            hashMap.put("questions", question);
            return new Result(hashMap, "修改成功");
        }
    }

    /**
     * 删除问题
     *
     * @param questionId 被删除的问题Id
     * @param request    获取当前用户属性
     * @return 成功/失败
     */
    @GetMapping("/question/{questionId}/delete")
    public Result delete(@PathVariable Long questionId, HttpServletRequest request) throws NoPermissionException, NotFoundException {
        return questionController.delete(questionId, request);
    }

    /**
     * 管理页根据标题、标签、用户昵称搜索 前端传入QuestionQuery对象和nickname
     *
     * @param pageable 分页对象
     * @param question 查询条件
     * @param nickname 查询的用户昵称
     * @return 查询结果
     */
    @PostMapping("searchQuestions")
    public Result searchQuestion(@PageableDefault(size = 1000, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                                 QuestionQuery question, String nickname) {
        Map<String, Object> hashMap = new HashMap<>(1);

        Page<Question> pages = questionService.listQuestionPlusNickname(pageable, question, nickname);

        //通过标题、标签、昵称查找
        hashMap.put("pages", pages);
        for (Question q : pages) {

            //得到发布问题的人
            User postUser = q.getUser();

            //这里到底要不要用计算力代替空间还要考虑
            q.setAvatar(postUser.getAvatar());
            q.setNickname(postUser.getNickname());
        }
        return new Result(hashMap, "搜索完成");
    }
}
