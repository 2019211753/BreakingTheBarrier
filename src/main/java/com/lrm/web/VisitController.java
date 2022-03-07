package com.lrm.web;

import com.lrm.exception.NotFoundException;
import com.lrm.po.User;
import com.lrm.service.FavoriteServiceImpl;
import com.lrm.service.UserServiceImpl;
import com.lrm.util.TokenInfo;
import com.lrm.vo.Archive;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 访问其它用户主页
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-31
 */
@RestController
public class VisitController {
    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    FavoriteServiceImpl favoriteServiceImpl;

    /**
     * @param userId 目标用户Id
     * @return 该用户的个人信息
     *
     * 隐私策略的种类
     * 1.关注/粉丝(following followed) 2.发布问题/博客/评论/点赞 3.收藏夹（open的收藏夹）
     * 111 = 7, 全开放; 110 = 6, 不开放收藏夹; 010 = 2 仅开放发布的问题/博客/评论/点赞
     * 共8种策略
     */
    @GetMapping("visit/{userId}")
    public Result visitUser(@PathVariable Long userId, HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(8);

        User receiveUser = userServiceImpl.getUser(userId);
        User postUser = userServiceImpl.getUser(TokenInfo.getCustomUserId(request));
        if (receiveUser == null) {
            throw new NotFoundException("未查询到该用户");
        }

        receiveUser.setFavorites(favoriteServiceImpl.getFavorites(true, userId));

        Integer privacyType = receiveUser.getPrivacyType();
        switch (privacyType) {
            case 1:
                hashMap.put("favorites", favoriteServiceImpl.getFavorites(true, userId));

                break;
            case 2:
                hashMap.put("questions", receiveUser.getQuestions());
                hashMap.put("blogs", receiveUser.getBlogs());
                hashMap.put("comments", Archive.getCommentMessages(receiveUser.getPostComments(), receiveUser));
                hashMap.put("likes", Archive.getLikesMessages(receiveUser.getPostLikes(), receiveUser));

                break;
            case 3:
                hashMap.put("questions", receiveUser.getQuestions());
                hashMap.put("blogs", receiveUser.getBlogs());
                hashMap.put("comments", Archive.getCommentMessages(receiveUser.getPostComments(), receiveUser));
                hashMap.put("likes", Archive.getLikesMessages(receiveUser.getPostLikes(), receiveUser));

                hashMap.put("favorites", favoriteServiceImpl.getFavorites(true, userId));

                break;
            case 4:
                hashMap.put("followingUsers", receiveUser.getFollowingUsers());
                hashMap.put("followedUsers", receiveUser.getFollowedUsers());

                break;
            case 5:
                hashMap.put("followingUsers", receiveUser.getFollowingUsers());
                hashMap.put("followedUsers", receiveUser.getFollowedUsers());

                hashMap.put("favorites", favoriteServiceImpl.getFavorites(true, userId));

                break;
            case 6:
                hashMap.put("followingUsers", receiveUser.getFollowingUsers());
                hashMap.put("followedUsers", receiveUser.getFollowedUsers());

                hashMap.put("questions", receiveUser.getQuestions());
                hashMap.put("blogs", receiveUser.getBlogs());
                hashMap.put("comments", Archive.getCommentMessages(receiveUser.getPostComments(), receiveUser));
                hashMap.put("likes", Archive.getLikesMessages(receiveUser.getPostLikes(), receiveUser));
                break;
            case 7:
                hashMap.put("followingUsers", receiveUser.getFollowingUsers());
                hashMap.put("followedUsers", receiveUser.getFollowedUsers());

                hashMap.put("questions", receiveUser.getQuestions());
                hashMap.put("blogs", receiveUser.getBlogs());
                hashMap.put("comments", Archive.getCommentMessages(receiveUser.getPostComments(), receiveUser));
                hashMap.put("likes", Archive.getLikesMessages(receiveUser.getPostLikes(), receiveUser));

                hashMap.put("favorites", favoriteServiceImpl.getFavorites(true, userId));

            default:

        }
        receiveUser.setAvailableNum(null);
        receiveUser.setPrivacyType(null);
        receiveUser.setAdmin(null);
        receiveUser.setCanSpeak(null);
        hashMap.put("userInfo", receiveUser);

        if (receiveUser.getFollowedUsers().contains(postUser) &&
                postUser.getFollowingUsers().contains(receiveUser)) {
            hashMap.put("following", true);
        } else {
            hashMap.put("following", false);
        }
        return new Result(hashMap, "");
    }
}
