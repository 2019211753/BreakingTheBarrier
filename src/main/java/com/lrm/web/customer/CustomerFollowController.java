package com.lrm.web.customer;

import com.lrm.po.User;
import com.lrm.service.UserServiceImpl;
import com.lrm.util.FileUtils;
import com.lrm.util.TokenInfo;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-09-04
 */
@RequestMapping("/customer")
@RestController
public class CustomerFollowController {
    @Value("${web.upload-path}")
    private String path;

    @Autowired
    UserServiceImpl userServiceImpl;

    @GetMapping("/followInfo")
    public Result getFollowInfo(HttpServletRequest request) {
        Long customUserId = TokenInfo.getCustomUserId(request);
        Map<String, Object> hashMap = new HashMap<>(2);
        User customUser = userServiceImpl.getUser(customUserId);

        List<User> followedUsers = insertUserAttribute(customUser.getFollowedUsers(), path);
        List<User> followingUsers = insertUserAttribute(customUser.getFollowingUsers(), path);

        hashMap.put("followedUsers", followedUsers);
        hashMap.put("followingUsers", followingUsers);

        return new Result(hashMap, "查询成功");
    }


    public static List<User> insertUserAttribute(List<User> users, String path) {
        List<User> newUsers = new ArrayList<>(users.size());
        for (User user1 : users) {
            User user2 = new User();
            user2.setId(user1.getId());
            user2.setDonation(user1.getDonation());
            user2.setNickname(user1.getNickname());
            user2.setAvatar(FileUtils.convertAvatar(path, user1.getAvatar()));
            user2.setFollowingUserNum(user1.getFollowingUserNum());
            user2.setFollowedUserNum(user1.getFollowedUserNum());
            newUsers.add(user2);
        }
        return newUsers;
    }

}
