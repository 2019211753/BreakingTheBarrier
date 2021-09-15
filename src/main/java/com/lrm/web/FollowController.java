package com.lrm.web;

import com.lrm.enumeration.DonationGrow;
import com.lrm.exception.FailedOperationException;
import com.lrm.exception.NotFoundException;
import com.lrm.po.User;
import com.lrm.service.UserServiceImpl;
import com.lrm.util.TokenInfo;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户关注
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-20
 */
@RestController
public class FollowController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * 取关/关注
     *
     * @param followedUserId 被关注的用户Id
     * @param request        获得当前用户Id
     * @return 取关/关注 成功/失败
     */
    @GetMapping("/follow/{followedUserId}")
    public Result followUser(@PathVariable Long followedUserId, HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(3);
        Long followingUserId = TokenInfo.getCustomUserId(request);
        User followingUser = userServiceImpl.getUser(followingUserId);
        User followedUser = userServiceImpl.getUser(followedUserId);

        if (followedUser == null) {
            throw new NotFoundException("未查询到该用户");
        }

        if (followedUser == followingUser) {
            throw new FailedOperationException("你不能关注你自己");
        }

        //取关
        if (followedUser.getFollowedUsers().contains(followingUser) &&
                followingUser.getFollowingUsers().contains(followedUser)) {
            followedUser.getFollowedUsers().remove(followingUser);
            followedUser.setFollowedUserNum(followedUser.getFollowedUserNum() - 1);
            followingUser.getFollowingUsers().remove(followedUser);
            followingUser.setFollowingUserNum(followingUser.getFollowingUserNum() - 1);
            followedUser.setDonation(followedUser.getDonation() - DonationGrow.FOLLOWED.getGrow());

            userServiceImpl.saveUser(followedUser);
            userServiceImpl.saveUser(followingUser);
            if (!followedUser.getFollowedUsers().contains(followingUser) &&
                    !followingUser.getFollowingUsers().contains(followedUser)) {
                hashMap.put("myFollowingNum", followingUser.getFollowingUsers().size());
                hashMap.put("hisFollowedNum", followedUser.getFollowedUsers().size());
                hashMap.put("following", false);
                return new Result(hashMap, "取关成功");
            } else {
                throw new FailedOperationException( "取关失败");
            }
        } else {
            //关注
            followedUser.getFollowedUsers().add(followingUser);
            followedUser.setFollowedUserNum(followedUser.getFollowedUserNum() + 1);
            followingUser.getFollowingUsers().add(followedUser);
            followingUser.setFollowingUserNum(followingUser.getFollowingUserNum() + 1);
            followedUser.setDonation(followedUser.getDonation() + DonationGrow.FOLLOWED.getGrow());

            userServiceImpl.saveUser(followedUser);
            userServiceImpl.saveUser(followingUser);
            if (followedUser.getFollowedUsers().contains(followingUser) &&
                    followingUser.getFollowingUsers().contains(followedUser)) {
                hashMap.put("myFollowingNum", followingUser.getFollowingUsers().size());
                hashMap.put("hisFollowedNum", followedUser.getFollowedUsers().size());
                hashMap.put("following", true);
                return new Result(hashMap, "关注成功");
            } else {
                throw new FailedOperationException( "关注失败");
            }
        }
    }
}
