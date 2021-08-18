package com.lrm.web.customer;

import com.lrm.exception.IllegalParameterException;
import com.lrm.po.User;
import com.lrm.service.UserServiceImpl;
import com.lrm.util.FileUtils;
import com.lrm.util.MD5Utils;
import com.lrm.util.StringVerify;
import com.lrm.util.TokenInfo;
import com.lrm.vo.Magic;
import com.lrm.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 个人信息管理
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@RequestMapping("/customer")
@RestController
public class ProfileController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Value("${web.upload-path}")
    private String path;


    /**
     * 返回个人信息
     *
     * @param request 获取token 获取当前用户id
     * @return user: 当前用户对象 ACAMEDIES 可选院系
     */
    @GetMapping("/personal")
    public Result showMe(HttpServletRequest request) {
        //这里返回的content里的base64编码可能耗内存 可以考虑优化 在后端存储图片路径
        Map<String, Object> hashMap = new HashMap<>(2);

        User user = new User();
        BeanUtils.copyProperties(userServiceImpl.getUser(TokenInfo.getCustomUserId(request)), user);

        //返回当前用户信息和院系选择
        hashMap.put("user", user);
        hashMap.put("ACADEMIES", Magic.ACADEMIES);

        return new Result(hashMap, "");
    }

    //下面两个资料修改最好分开

    /**
     * 上传头像到本地 获取path返回
     *
     * @param req  获取当前用户id
     * @param file 被上传的文件
     * @return avatar 文件在服务器端的路径
     */
    @PostMapping("/uploadAvatar")
    public Result uploadAvatar(MultipartFile file, HttpServletRequest req) throws IOException {
        Map<String, Object> hashMap = new HashMap<>(1);

        Long userId = TokenInfo.getCustomUserId(req);

        //创建存放文件的文件夹的流程

        //头像文件夹的绝对路径
        String realPath = path + "/" + userId + "/avatar";

        //所上传的文件原名
        String oldName = file.getOriginalFilename();

        //保存文件到文件夹中 获得新文件名
        FileUtils.rebuildFolder(realPath);
        String newName = FileUtils.upload(file, realPath, oldName);

        User user = userServiceImpl.getUser(userId);
        user.setAvatar("images/" + userId + "/avatar/" + newName);
        userServiceImpl.saveUser(user);

        hashMap.put("avatar", "images/" + userId + "/avatar/" + newName);
        return new Result(hashMap, "上传成功");
    }


    /**
     * 修改发送过来的信息
     * 最后检查昵称是否已经存在，存在的话就改其他的，返回前端昵称已存在。
     * 最后检查密码是否符合格式规范。
     *
     * @param request           获取当前用户Id
     * @param user1 前端封装好的user对象
     * @return 新token
     */
    @PostMapping("/modifyAll")
    public Result modifyUserInformation(HttpServletRequest request, @RequestBody User user1) {
        Map<String, Object> hashMap = new HashMap<>(1);

        StringBuilder errorMessage = null;

        //获得当前用户Id 检查用户需要更改的昵称有没用其他用户在使用
        Long customerUserId = TokenInfo.getCustomUserId(request);
        String nickname = user1.getNickname();
        String password = user1.getPassword();
        String email = user1.getEmail();
        String qqId = user1.getQqId();
        String wechatId = user1.getWechatId();
        Boolean sex = user1.getSex();
        String personalSignature = user1.getPersonalSignature();
        String academy = user1.getAcademy();
        String major = user1.getMajor();
        Integer privacyType = user1.getPrivacyType();

        User user0 = userServiceImpl.getUser(user1.getNickname());

        User user = new User();
        user.setId(customerUserId);

        //填进去
        if (!"".equals(email) && email != null) {
            user.setEmail(email);
        }

        if (!"".equals(qqId) && qqId != null) {
            user.setQqId(qqId);
        }

        if (!"".equals(wechatId) && wechatId != null) {
            user.setWechatId(wechatId);
        }

        user.setSex(sex);

        if (!"".equals(personalSignature) && personalSignature != null) {
            user.setPersonalSignature(personalSignature);
        }

        if (!"".equals(academy) && academy != null) {
            user.setAcademy(academy);
        }

        if (!"".equals(major) && major != null) {
            user.setMajor(major);
        }

        if (privacyType != null) {
            if (privacyType > 7) {
                user.setPrivacyType(7);
            } else if (privacyType < 0) {
                user.setPrivacyType(0);
            } else {
                user.setPrivacyType(privacyType);
            }

        }

        if (!"".equals(password) && password != null) {
            if (StringVerify.isContainChinese(password) && (password.length() > 12 || password.length() < 7)) {
                user.setPassword("M#D5+" + MD5Utils.code(password));
            } else {
                errorMessage = new StringBuilder("密码格式错误；");
            }
        }

        if (!(user0 != null && user0.getId().equals(customerUserId))) {
            user.setNickname(nickname);
            userServiceImpl.updateUser(user, hashMap);
        } else {
            userServiceImpl.updateUser(user, hashMap);

            if (errorMessage == null) {
                errorMessage = new StringBuilder("昵称已被占用；");
            } else {
                errorMessage.append("昵称已被占用；");
            }
        }

        if (errorMessage != null) {
            throw new IllegalParameterException(errorMessage.append("其他信息修改成功；").toString());
        }

        return new Result(hashMap, "修改成功");
    }




}
