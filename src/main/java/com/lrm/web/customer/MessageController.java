package com.lrm.web.customer;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lrm.exception.NotFoundException;
import com.lrm.po.Comment;
import com.lrm.po.Likes;
import com.lrm.po.User;
import com.lrm.service.CommentServiceImpl;
import com.lrm.service.LikesServiceImpl;
import com.lrm.service.UserServiceImpl;
import com.lrm.util.TokenInfo;
import com.lrm.vo.Archive;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息通知管理
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@RequestMapping("/customer/messages")
@RestController
public class MessageController {

    @Autowired
    private CommentServiceImpl commentServiceImpl;

    @Autowired
    private LikesServiceImpl likesServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * 返回所有通知
     *
     * @param request 获取当前用户id
     * @return 未读评论和点赞
     * @throws JWTVerificationException JWT鉴权错误
     */
    @GetMapping("/")
    public Result messages(HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(4);

        Long userId = TokenInfo.getCustomUserId(request);
        User currentUser = userServiceImpl.getUser(userId);

        //已读评论
        List<Comment> lookedComments = commentServiceImpl.listComments(userId, true);
        //自己评论自己会默认已读，需要在已读评论中去除掉
        lookedComments.removeIf(comment -> comment.getPostUser() == comment.getReceiveUser());
        //本来是按id排的，实际上就是创建时间，为实现创建时间逆序，直接反序
        Collections.reverse(lookedComments);
        List<Archive> lookedCommentArchives = Archive.getCommentMessages(lookedComments, currentUser);

        //未读评论
        List<Comment> unLookedComments = commentServiceImpl.listComments(userId, false);
        Collections.reverse(unLookedComments);
        List<Archive> unLookedCommentArchives = Archive.getCommentMessages(unLookedComments, currentUser);

        //已读点赞
        List<Likes> lookedLikes = likesServiceImpl.list(userId, true);
        lookedLikes.removeIf(likes1 -> likes1.getPostUser() == likes1.getReceiveUser());
        Collections.reverse(lookedLikes);
        List<Archive> lookedLikesArchives = Archive.getLikesMessages(lookedLikes, currentUser);

        //未读点赞
        List<Likes> unLookedLikes = likesServiceImpl.list(userId, false);
        Collections.reverse(unLookedLikes);
        List<Archive> unLookedLikesArchives = Archive.getLikesMessages(unLookedLikes, currentUser);

        hashMap.put("lookedComments", lookedCommentArchives);
        hashMap.put("unLookedComments", unLookedCommentArchives);
        hashMap.put("lookedLikes", lookedLikesArchives);
        hashMap.put("unLookedLikes", unLookedLikesArchives);

        return new Result(hashMap, "");
    }

    /**
     * 已读单个评论
     *
     * @param commentId 评论id
     */
    @GetMapping("/comment/{commentId}/read")
    public Result readComment(@PathVariable Long commentId, HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(1);
        Long userId = TokenInfo.getCustomUserId(request);
        User currentUser = userServiceImpl.getUser(userId);

        Comment comment = commentServiceImpl.getComment(commentId);
        if (comment == null) {
            throw new NotFoundException("未查询到该评论");
        }
        comment.setLooked(true);
        commentServiceImpl.saveComment(comment);

        return getResult(hashMap, currentUser, commentServiceImpl.countUnLooked(userId), userId);
    }

    /**
     * 已读单个点赞
     *
     * @param likesId 点赞id
     */
    @GetMapping("/likes/{likesId}/read")
    public Result readLikes(@PathVariable Long likesId, HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(1);
        Long userId = TokenInfo.getCustomUserId(request);
        User currentUser = userServiceImpl.getUser(userId);

        Likes likes = likesServiceImpl.get(likesId);
        if (likes == null) {
            throw new NotFoundException("未查询到该点赞");
        }
        likes.setLooked(true);
        likesServiceImpl.save(likes);

        return getResult(hashMap, currentUser, likesServiceImpl.countUnLooked(userId), userId);

    }

    /**
     * 已读所有未读的评论
     *
     * @param request 获取当前用户id
     */
    @GetMapping("/readAllComments")
    public Result readAllComments(HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(1);
        Long userId = TokenInfo.getCustomUserId(request);
        User currentUser = userServiceImpl.getUser(userId);

        List<Comment> comments = commentServiceImpl.listComments(userId, false);
        for (Comment comment : comments) {
            comment.setLooked(true);
            commentServiceImpl.saveComment(comment);
        }
        return getResult(hashMap, currentUser, commentServiceImpl.countUnLooked(userId), userId);
    }

    /**
     * 已读所有未读的点赞
     *
     * @param request 获取当前用户id
     */
    @GetMapping("/readAllLikes")
    public Result readAllLikes(HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(1);
        Long userId = TokenInfo.getCustomUserId(request);
        User currentUser = userServiceImpl.getUser(userId);

        List<Likes> likes = likesServiceImpl.list(userId, false);
        for (Likes likes1 : likes) {
            likes1.setLooked(true);
            likesServiceImpl.save(likes1);
        }
        return getResult(hashMap, currentUser, likesServiceImpl.countUnLooked(userId), userId);
    }

    private Result getResult(Map<String, Object> hashMap, User currentUser, Integer integer, Long userId) {
        Map<String, Object> infos = new HashMap<>(1);
        infos.put("unLookedInforms", integer);
        String token = TokenInfo.postToken(currentUser, infos);
        hashMap.put("token", token);
        return new Result(hashMap, "已读成功");
    }
}

