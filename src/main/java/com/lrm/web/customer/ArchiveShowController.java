package com.lrm.web.customer;

import com.lrm.po.Comment;
import com.lrm.po.Likes;
import com.lrm.po.Template;
import com.lrm.po.User;
import com.lrm.service.BlogServiceImpl;
import com.lrm.service.QuestionServiceImpl;
import com.lrm.service.TemplateServiceImpl;
import com.lrm.service.UserServiceImpl;
import com.lrm.util.TokenInfo;
import com.lrm.vo.Archive;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 归档
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@RestController
@RequestMapping("/customer")
public class ArchiveShowController {
    @Autowired
    private QuestionServiceImpl questionServiceImpl;

    @Autowired
    private BlogServiceImpl blogServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * 按年份归档 时间逆序
     *
     * @param request 获取当前用户id
     * @return 已经分类的问题
     */
    @GetMapping("/archivesQuestion")
    public Result archivesQuestion(HttpServletRequest request) {
        return archives(request, questionServiceImpl);
    }

    /**
     * 按年份归档 时间逆序
     *
     * @param request 获取当前用户id
     * @return 已经分类的博客
     */
    @GetMapping("/archivesBlog")
    public Result archivesBlog(HttpServletRequest request) {
        return archives(request, blogServiceImpl);
    }

    <T extends Template> Result archives(HttpServletRequest request, TemplateServiceImpl<T> templateServiceImpl) {
        Map<String, Object> hashMap = new HashMap<>(2);

        Long userId = TokenInfo.getCustomUserId(request);

        hashMap.put("archiveMap", templateServiceImpl.archivesByUserId(userId));
        hashMap.put("count", templateServiceImpl.countAllByUserId(userId));

        return new Result(hashMap, "");
    }

    /**
     * 按年份归档 时间逆序
     *
     * @return 用户发布的评论
     */
    @GetMapping("/archiveComment")
    public Result archiveComment(HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(1);

        Long userId = TokenInfo.getCustomUserId(request);

        User user = userServiceImpl.getUser(userId);
        List<Comment> postComments = user.getPostComments();
        //本来是按id排的，实际上就是创建时间，为实现创建时间逆序，直接反序
        Collections.reverse(postComments);
        hashMap.put("postComments", Archive.getCommentMessages(postComments));
        return new Result(hashMap, "");
    }

    /**
     * 按年份归档 时间逆序
     *
     * @return 用户发布的点赞
     */
    @GetMapping("/archiveLike")
    public Result archiveLike(HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(1);

        Long userId = TokenInfo.getCustomUserId(request);

        User user = userServiceImpl.getUser(userId);
        List<Likes> postLikes = user.getPostLikes();
        Collections.reverse(postLikes);
        hashMap.put("postLikes", Archive.getLikesMessages(postLikes));
        return new Result(hashMap, "");
    }
}
