package com.lrm.web;

import com.lrm.exception.FailedOperationException;
import com.lrm.exception.IllegalParameterException;
import com.lrm.po.User;
import com.lrm.service.UserServiceImpl;
import com.lrm.util.TokenInfo;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 注册登录
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@Validated
@RestController
public class LoginController {
    @Value("${web.upload-path}")
    private String path;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * 注册
     * 为什么用@Validated就能进入方法被BindingResult“捕获”，@Valid不行？也许是用了自定义校验器
     *
     * @param user 前端封装好的User对象 包含账号、密码、昵称
     * @return 返回注册成功得到的User对象 需要确定泛型，否则操作无效了; 返回注册失败的报错信息
     */
    @PostMapping("/register")
    public Result register(@RequestBody @Validated User user, BindingResult bindingResult) {
        Map<String, Object> hashMap = new HashMap<>(1);

        if (bindingResult.hasErrors()) {
            throw new IllegalParameterException(IllegalParameterException.getMessage(bindingResult));
        }

        String username = user.getUsername();
        String password = user.getPassword();
        String nickname = user.getNickname();

        //先检查是否已经注册过。注册过报错；没注册过注册，注册成功跳转到登录。
        User user1 = userServiceImpl.checkRegister(username, nickname);
        if (user1 != null) {
            //跳转到注册页面
            throw new FailedOperationException("该账号或昵称已被注册过");
        } else {

            userServiceImpl.saveUser(username, password, nickname);

            //又给它整回去
            hashMap.put("user", user);

            //跳转到登录页面
            return new Result(hashMap, "注册成功");
        }
    }

    /**
     * 登录
     *
     * @param user 前端封装的User对象
     * @return 登录成功的token; 登陆失败的报错信息
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) throws IOException {
        //需要传递到前端的 包含在token内的信息 map用来存放payload
        //或传递报错信息
        Map<String, Object> hashMap = new HashMap<>(1);

        String username = user.getUsername();
        String password = user.getPassword();
        StringBuilder errorMsg = new StringBuilder(64);
        if (username == null & password != null) {
            errorMsg.append("请输入账号；");
            throw new IllegalParameterException(new String(errorMsg));

        } else if (username == null & password == null) {
            errorMsg.append("请输入账号；");
        }
        if (password == null)
        {
            errorMsg.append("请输入密码；");
            throw new IllegalParameterException(new String(errorMsg));
        }

        //检查账号和密码在数据库中存在不。(不考虑是否注册过了)。存在登录；不存在报错。
        User user1 = userServiceImpl.checkUser(username, password);
        if(user1 != null)
        {
            String token = TokenInfo.postToken(user1, path);

            hashMap.put("token", token);
            //返回首页
            return new Result(hashMap, "登录成功");
        } else {
            //返回登录页面
            throw new FailedOperationException("账号或密码错误");
        }
    }

}
