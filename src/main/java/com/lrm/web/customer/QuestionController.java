package com.lrm.web.customer;

import com.lrm.exception.FailedOperationException;
import com.lrm.exception.IllegalParameterException;
import com.lrm.exception.NoPermissionException;
import com.lrm.exception.NotFoundException;
import com.lrm.po.Question;
import com.lrm.po.User;
import com.lrm.service.QuestionService;
import com.lrm.service.TagService;
import com.lrm.service.UserService;
import com.lrm.util.FileUtils;
import com.lrm.util.TokenInfo;
import com.lrm.vo.QuestionQuery;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 个人问题管理
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@RequestMapping("/customer")
@RestController
public class QuestionController {
    @Autowired
    private TagService tagService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    /**
     * 个人主页搜索 根据标题+标签 返回个人发出的问题
     * 跟上面那个get方法的不同就是 一个question是空的 一个不是空的
     *
     * @param request  获得当前用户id
     * @param pageable 分页标准
     * @param question 封装的query对象
     * @return 查询所得问题分页
     */
    @PostMapping("/searchQuestions")
    public Result search(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         QuestionQuery question, HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(1);

        //得到当前用户的昵称
        String nickname = TokenInfo.getCustomNickname(request);

        hashMap.put("pages", questionService.listQuestionPlusNickname(pageable, question, nickname));

        return new Result(hashMap, "搜索完成");
    }

    /**
     * 新增问题 初始化各部分属性
     *
     * @param request       获得当前用户id
     * @param question      前端封装的question对象
     * @param bindingResult 配合@Valid检测是否为空
     * @return 报错信息/成功信息
     */
    @PostMapping("/questions")
    public Result post(@Valid Question question, BindingResult bindingResult, HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(1);

        Long userId = TokenInfo.getCustomUserId(request);
        User user = userService.getUser(userId);

        if (user.getCanSpeak()) {
            //后端检验valid 如果校验失败 返回input页面
            if (bindingResult.hasErrors()) {
                throw new IllegalParameterException(IllegalParameterException.getMessage(bindingResult));
            }

            question.setUser(user);

            //令前端只传回tagIds而不是tag对象 将它转换为List<Tag> 在service层找到对应的Tag保存到数据库
            question.setTags(tagService.listTag(question.getTagIds()));
            Question q;

            if (question.getId() == null) {
                q = questionService.saveQuestion(question, user);
            } else {
                hashMap.put("question", question);
                throw new FailedOperationException("该问题已存在");
            }

            if (q != null) {
                hashMap.put("question", question);
                return new Result(hashMap, "发布成功");
            } else {
                throw new FailedOperationException("发布失败");
            }
        } else {
            throw new NoPermissionException("你无权发布问题");
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
    public Result delete(@PathVariable Long questionId, HttpServletRequest request) throws
            NoPermissionException, NotFoundException {
        Long userId = TokenInfo.getCustomUserId(request);
        Boolean admin = TokenInfo.isAdmin(request);

        Question question = questionService.getQuestion(questionId);

        if (question == null) {
            throw new NotFoundException("未查询到该问题");
        }
        if ((!question.getUser().getId().equals(userId)) && (!admin)) {
            throw new NoPermissionException("你无权删除该问题");
        }

        return getMapResult(questionId, questionService);
    }

    public static Result getMapResult(@PathVariable Long questionId, QuestionService questionService) {
        Question question;

        questionService.deleteQuestion(questionId);

        question = questionService.getQuestion(questionId);
        if (question != null) {
            throw new FailedOperationException("删除失败");
        } else {
            return new Result(null, "删除成功");
        }
    }

    /**
     * 问题内容的图片上传
     *
     * @param req        获得当前用户id
     * @param files      多文件上传
     * @param questionId 发布问题的Id
     * @return 多文件在本地的路径
     * @throws IOException 文件大小溢出
     */
    @PostMapping("/uploadPhotos")
    public Result uploadPhotos(MultipartFile[] files, HttpServletRequest req, Long questionId) throws IOException {
        Map<String, Object> hashMap = new HashMap<>(files.length);

        //创建存放文件的文件夹的流程
        Long userId = TokenInfo.getCustomUserId(req);
        SimpleDateFormat sdf = new SimpleDateFormat("/yyyy-MM-dd/");
        String format = sdf.format(new Date());
        String path = "/upload/" + userId + "/questions/" + questionId + format;

        //新文件夹目录绝对路径
        String realPath = req.getServletContext().getRealPath(path);
        File folder = new File(req.getServletContext().getRealPath("/upload/" + userId + "/questions/" + questionId));

        //如果文件夹不存在，创建文件夹 否则删除文件夹
        if (folder.exists()) {
            FileUtils.deleteFile(folder);
        }

        List<String> pathList = new ArrayList<>();
        for (MultipartFile uploadFile : files) {
            folder = new File(realPath);
            if (!folder.isDirectory()){
                folder.mkdirs();
            }

            //保存文件到文件夹中

            //所上传的文件原名
            String oldName = uploadFile.getOriginalFilename();

            //新文件名
            String newName = UUID.randomUUID().toString()+oldName.substring(oldName.lastIndexOf("."));
            uploadFile.transferTo(new File(folder, newName));
            pathList.add(realPath + newName);
        }

        hashMap.put("photos", pathList);
        return new Result(hashMap, "上传成功");
    }

}
