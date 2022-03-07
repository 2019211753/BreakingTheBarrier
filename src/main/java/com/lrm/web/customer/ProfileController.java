package com.lrm.web.customer;

import com.lrm.exception.IllegalParameterException;
import com.lrm.po.User;
import com.lrm.service.UserServiceImpl;
import com.lrm.util.*;
import com.lrm.vo.Magic;
import com.lrm.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
    @Value("${oss.endpoint_user_info}")
    private String endpoint;

    @Value("${oss.accessKeyId_user_info}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret_user_info}")
    private String accessKeySecret;

    @Value("${oss.bucketName_user_info}")
    private String bucketName;

    @Value("${oss.urlPrefix_user_info}")
    private String urlPrefix;

    @Autowired
    private UserServiceImpl userServiceImpl;

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
        user.setAvatar(user.getAvatar());

        //返回当前用户信息和院系选择
        hashMap.put("user", user);
        hashMap.put("ACADEMIES", Magic.ACADEMIES);

        return new Result(hashMap, "");
    }

    //下面三个资料修改最好分开

    /**
     * 上传头像到oss 获取url返回
     *
     * @param req  获取当前用户id
     * @param file 被上传的文件
     * @return avatar 头像的url
     */
    @PostMapping("/uploadAvatar")
    public Result uploadAvatar(MultipartFile file, HttpServletRequest req) throws IOException {
        Map<String, Object> hashMap = new HashMap<>(1);
        Long userId = TokenInfo.getCustomUserId(req);

        //限制图片大小
        long size = file.getSize();
        if (size / 1024 > Magic.MAX_UPLOAD_AVATAR_SIZE_BYTES) {
            throw new IllegalParameterException("图片大小为：" + size + "KB，超过了" + Magic.MAX_UPLOAD_AVATAR_SIZE_BYTES + "KB");
        }

        if (file.getOriginalFilename() == null) {
            throw new IllegalParameterException("请为图片命名");
        }

        String suffix = FileTypeUtils.getFileType(file);
        if (!"PNG".equalsIgnoreCase(suffix) &&
            !"JPG".equalsIgnoreCase(suffix) &&
            !"JPEG".equalsIgnoreCase(suffix) &&
            !"BMP".equalsIgnoreCase(suffix) &&
            !"GIF".equalsIgnoreCase(suffix)) {

            throw new IllegalArgumentException("请上传类型合法的图片");
        }

        String catalog = userId + "/avatar/";
        //先删了，一个用户只需要一个头像
        OSSUtils.deleteFile(endpoint, accessKeyId, accessKeySecret, bucketName, catalog);
        //再创建目录
        String fileName = FileUtils.getFileName(file.getOriginalFilename());
        OSSUtils.uploadFile(file, endpoint, accessKeyId, accessKeySecret, bucketName,
                catalog, fileName);

        String avatarURL = "https://" + urlPrefix + "/" + catalog + fileName;
        User user = userServiceImpl.getUser(userId);
        user.setAvatar(avatarURL);
        userServiceImpl.saveUser(user);

        hashMap.put("avatar", avatarURL);

        return new Result(hashMap, "上传成功");
    }

    /**
     * 上传头像到oss 获取url返回
     *
     * @param req  获取当前用户id
     * @param file 被上传的文件
     * @return avatar 头像的url
     */
    @PostMapping("/uploadPayCode")
    public Result uploadPayCode(MultipartFile file, HttpServletRequest req) throws IOException {
        Map<String, Object> hashMap = new HashMap<>(1);
        Long userId = TokenInfo.getCustomUserId(req);

        //限制图片大小
        long size = file.getSize();
        if (size / 1024 > Magic.MAX_UPLOAD_AVATAR_SIZE_BYTES) {
            throw new IllegalParameterException("图片大小为：" + size + "KB，超过了" + Magic.MAX_UPLOAD_PAY_CODE_SIZE_BYTES + "KB");
        }

        if (file.getOriginalFilename() == null) {
            throw new IllegalParameterException("请为文件命名");
        }

        String suffix = FileTypeUtils.getFileType(file);
        if (!"PNG".equalsIgnoreCase(suffix) &&
            !"JPG".equalsIgnoreCase(suffix) &&
            !"JPEG".equalsIgnoreCase(suffix) &&
            !"BMP".equalsIgnoreCase(suffix) &&
            !"GIF".equalsIgnoreCase(suffix)) {
            throw new IllegalArgumentException("请上传类型合法的图片");
        }

        String catalog = userId + "/wechatPayCode/";
        //先删了，一个用户只需要一个头像
        OSSUtils.deleteFile(endpoint, accessKeyId, accessKeySecret, bucketName, catalog);
        //再创建目录
        String fileName = FileUtils.getFileName(file.getOriginalFilename());
        OSSUtils.uploadFile(file, endpoint, accessKeyId, accessKeySecret, bucketName,
                catalog, fileName);

        String url = "https://" + urlPrefix + "/" + catalog + fileName;
        User user = userServiceImpl.getUser(userId);
        user.setWechatPayCode(url);
        userServiceImpl.saveUser(user);

        hashMap.put("payCode", url);

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
    public Result modifyUserInformation(HttpServletRequest request, @RequestBody User user1) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
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

        if (sex != null) {
            user.setSex(sex);
        }

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
            if (StringVerify.isContainChinese(password) || (password.length() > 12 || password.length() < 7)) {
                errorMessage = new StringBuilder("密码格式错误；");
            } else {
                user.setPassword("M#D5+" + MD5Utils.code(password));
            }
        }

        if (!"".equals(nickname) && nickname != null) {
            User user0 = userServiceImpl.getUser(user1.getNickname());
            //如果有昵称是这个的用户并且这个用户不是当前用户
            if ((user0 != null && !user0.getId().equals(customerUserId))) {
                if (errorMessage == null) {
                    errorMessage = new StringBuilder("昵称已被占用；");
                } else {
                    errorMessage.append("昵称已被占用；");
                }
            } else {
                user.setNickname(nickname);
            }
        }

        User newUser = userServiceImpl.updateUser(user);
        hashMap.put("token", TokenInfo.postToken(newUser));

        if (errorMessage != null) {
            errorMessage.append("其他信息修改成功；");
            return new Result(hashMap, errorMessage.toString());
        }

        return new Result(hashMap, "修改成功");
    }




}
