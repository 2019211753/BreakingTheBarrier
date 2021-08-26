package com.lrm.web;

import com.lrm.po.User;
import com.lrm.service.UserServiceImpl;
import com.lrm.util.TokenInfo;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 排行榜
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@RestController
public class RankController {
    @Autowired
    UserServiceImpl userServiceImpl;

    /**
     * @return 按贡献值排序的用户集合 选择前十
     */
    @GetMapping("/rank")
    Result donationRank(HttpServletRequest request) {
        Long customUserId = TokenInfo.getCustomUserId(request);

        Map<String, Object> hashMap = new HashMap<>(1);

        //返回十个贡献度最高的用户
        List<User> users = userServiceImpl.listTopUsers(10);

        //真正要返回的数据
        List<User> newUsers = new ArrayList<>(10);
        for (User user : users) {
            //规范需要返回啥数据
            User model = new User();
            model.setDonation(user.getDonation());
            model.setNickname(user.getNickname());
            model.setAvatar(user.getAvatar());
            model.setFollowingUserNum(user.getFollowingUserNum());
            model.setFollowedUserNum(user.getFollowedUserNum());
            newUsers.add(model);
        }
        //返回当前自己的排名
        Long myRank = userServiceImpl.getRank(userServiceImpl.getUser(customUserId).getDonation());
        hashMap.put("users", newUsers);
        hashMap.put("myRank", myRank);
        System.out.println(users);
        return new Result(hashMap, null);
    }
}
