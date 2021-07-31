package com.lrm.web;

import com.lrm.po.User;
import com.lrm.service.FavoriteService;
import com.lrm.service.UserService;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
    UserService userService;
    @Autowired
    FavoriteService favoriteService;

    /**
     * @param userId 目标用户Id
     * @return 该用户的个人信息
     *
     * 隐私策略的种类
     * 1.关注/粉丝(following followed) 2.发布问题/评论/点赞 3.收藏夹（open的收藏夹）
     * 111 = 7, 全开放; 110 = 6, 不开放收藏夹; 010 = 2 仅开放发布的问题/评论/点赞
     * 共8种策略
     */
    @GetMapping("visit/{userId}")
    public Result visitUser(@PathVariable Long userId) {
        Map<String, Object> hashMap = new HashMap<>(1);

        User user = userService.getUser(userId);
        user.setPostDisLikes(null);
        user.setReceiveComments(null);
        user.setReceiveLikes(null);
        user.setFavorites(favoriteService.getFavorites(true, userId));

        Integer privacyType = user.getPrivacyType();
        switch (privacyType) {
            case 0:
                user.setFollowedUsers(null);
                user.setFollowingUsers(null);

                user.setQuestions(null);
                user.setPostComments(null);
                user.setPostLikes(null);

                user.setFavorites(null);

                break;
            case 1:
                user.setFollowedUsers(null);
                user.setFollowingUsers(null);

                user.setQuestions(null);
                user.setPostComments(null);
                user.setPostLikes(null);

                break;
            case 2:
                user.setFollowedUsers(null);
                user.setFollowingUsers(null);

                user.setFavorites(null);

                break;
            case 3:
                user.setFollowedUsers(null);
                user.setFollowingUsers(null);

                break;
            case 4:
                user.setQuestions(null);
                user.setPostComments(null);
                user.setPostLikes(null);

                user.setFavorites(null);

                break;
            case 5:
                user.setQuestions(null);
                user.setPostComments(null);
                user.setPostLikes(null);

                break;
            case 6:
                user.setFavorites(null);

                break;
            default:

        }
        hashMap.put("user", user);
        return new Result(hashMap, "");
    }
}
