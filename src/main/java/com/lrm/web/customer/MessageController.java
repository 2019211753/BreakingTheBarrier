package com.lrm.web.customer;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lrm.po.Comment;
import com.lrm.po.Likes;
import com.lrm.po.User;
import com.lrm.service.CommentServiceImpl;
import com.lrm.service.LikesServiceImpl;
import com.lrm.util.TokenInfo;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
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

        //已读评论
        List<Comment> lookedComments = commentServiceImpl.listComments(userId, true);

        //使用iterator避免ConcurrentModificationException异常
        Iterator<Comment> iterator = lookedComments.iterator();
        while(iterator.hasNext()) {
            Comment comment = iterator.next();
            if (comment.getPostUser() == comment.getReceiveUser()) {
                iterator.remove();
                continue;
            }
            User postUser = comment.getPostUser();
            comment.setAvatar(postUser.getAvatar());
            comment.setNickname(postUser.getNickname());
        }

        //未读评论
        List<Comment> unLookedComments = commentServiceImpl.listComments(userId, false);

        for (Comment comment : unLookedComments) {
            User postUser = comment.getPostUser();
            comment.setAvatar(postUser.getAvatar());
            comment.setNickname(postUser.getNickname());
        }

        //已读点赞
        List<Likes> lookedLikes = likesServiceImpl.list(userId, true);

        Iterator<Likes> iterator1 = lookedLikes.iterator();
        while(iterator1.hasNext()) {
            Likes likes1 = iterator1.next();
            if (likes1.getPostUser() == likes1.getReceiveUser()) {
                iterator1.remove();
                continue;
            }
            User postUser = likes1.getPostUser();
            likes1.setAvatar(postUser.getAvatar());
            likes1.setNickname(postUser.getNickname());
        }

        //未读点赞
        List<Likes> unLookedLikes = likesServiceImpl.list(userId, false);

        for (Likes likes1 : unLookedLikes) {
            User postUser = likes1.getPostUser();
            likes1.setAvatar(postUser.getAvatar());
            likes1.setNickname(postUser.getNickname());
        }

        hashMap.put("lookedComments", lookedComments);
        hashMap.put("unLookedComments", unLookedComments);
        hashMap.put("lookedLikes", lookedLikes);
        hashMap.put("unLookedLikes", unLookedLikes);

        return new Result(hashMap, "");
    }

    /**
     * 已读单个评论
     *
     * @param commentId 评论id
     */
    @GetMapping("/comment/{commentId}/read")
    public void readComment(@PathVariable Long commentId) {
        Comment comment = commentServiceImpl.getComment(commentId);
        comment.setLooked(true);
        commentServiceImpl.saveComment(comment);
    }

    /**
     * 已读单个点赞
     *
     * @param likesId 点赞id
     */
    @GetMapping("/likes/{likesId}/read")
    public void readLikes(@PathVariable Long likesId) {
        Likes likes = likesServiceImpl.get(likesId);
        likes.setLooked(true);
        likesServiceImpl.save(likes);
    }

    /**
     * 已读所有未读的评论
     *
     * @param request 获取当前用户id
     */
    @GetMapping("/readAllComments")
    public void readAllComments(HttpServletRequest request) {
        Long userId = TokenInfo.getCustomUserId(request);
        List<Comment> comments = commentServiceImpl.listComments(userId, false);
        for (Comment comment : comments) {
            comment.setLooked(true);
            commentServiceImpl.saveComment(comment);
        }
    }

    /**
     * 已读所有未读的点赞
     *
     * @param request 获取当前用户id
     */
    @GetMapping("/readAllLikes")
    public void readAllLikes(HttpServletRequest request) {
        Long userId = TokenInfo.getCustomUserId(request);

        List<Likes> likes = likesServiceImpl.list(userId, false);
        for (Likes likes1 : likes) {
            likes1.setLooked(true);
            likesServiceImpl.save(likes1);
        }
    }
}

