package com.lrm.web.customer;

import com.lrm.po.User;
import com.lrm.service.UserServiceImpl;
import com.lrm.util.TokenInfo;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类<code>Doc</code>用于：TODO
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-09-04
 */
@RequestMapping("/customer")
@RestController
public class CustomerFollowController {
    @Autowired
    UserServiceImpl userServiceImpl;

    public static List<User> insertUserAttribute(List<User> users) {
        List<User> newUsers = new ArrayList<>(users.size());
        for (User user1 : users) {
            User user2 = new User();
            user2.setId(user1.getId());
            user2.setDonation(user1.getDonation());
            user2.setNickname(user1.getNickname());
            user2.setAvatar(user1.getAvatar());
            user2.setFollowingUserNum(user1.getFollowingUserNum());
            user2.setFollowedUserNum(user1.getFollowedUserNum());
            newUsers.add(user2);
        }
        return newUsers;
    }

    @GetMapping("/followInfo")
    public Result getFollowInfo(HttpServletRequest request) {
        Long customUserId = TokenInfo.getCustomUserId(request);
        Map<String, Object> hashMap = new HashMap<>(2);
        User customUser = userServiceImpl.getUser(customUserId);

        List<User> followedUsers = insertUserAttribute(customUser.getFollowedUsers());
        List<User> followingUsers = insertUserAttribute(customUser.getFollowingUsers());

        hashMap.put("followedUsers", followedUsers);
        hashMap.put("followingUsers", followingUsers);

        return new Result(hashMap, "查询成功");
    }
}
