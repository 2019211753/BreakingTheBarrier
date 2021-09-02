package com.lrm.web.admin;

import com.lrm.exception.NotFoundException;
import com.lrm.po.User;
import com.lrm.service.UserServiceImpl;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理员控制用户
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@RestController
@RequestMapping("/admin")
public class UserController {
    @Autowired
    UserServiceImpl userServiceImpl;

    /**
     * 查找用户
     *
     * @param nickname 昵称
     * @return 查询得到的用户
     */
    @PostMapping("/searchUser")
    public Result searchCustomer(@RequestBody Map<String, String> nickname) {
        Map<String, Object> hashMap = new HashMap<>(1);

        hashMap.put("user", userServiceImpl.getUser(nickname.get("nickname")));

        return new Result(hashMap, "搜索完成");
    }

    /**
     * 禁言/解禁
     *
     * @param userId 用户Id
     */
    @GetMapping("/controlSpeak/{userId}")
    public void controlSpeak(@PathVariable Long userId) {
        User user = userServiceImpl.getUser(userId);
        if (user == null) {
            throw new NotFoundException("未查询到该用户");
        }
        user.setCanSpeak(!user.getCanSpeak());
        userServiceImpl.saveUser(user);
    }
}
