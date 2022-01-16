package com.lrm.service;

import com.lrm.dao.UserRepository;
import com.lrm.po.Favorite;
import com.lrm.po.User;
import com.lrm.util.MD5Utils;
import com.lrm.util.MyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@Service
public class UserServiceImpl implements UserService
{
    /**
     *  依赖注入 在某类中应用其他类的方法 需要调用这个类的对象 这就是依赖
     */
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FavoriteServiceImpl favoriteServiceImpl;

    //验证该用户是否已经注册
    //注册了的不能再注册（账号或昵称是否已经存在）
    //注册了的可以直接登录


    /**
     * 保存到数据库
     *
     * @param username 账号
     * @param password 密码
     * @param nickname 昵称
     */
    @Override
    @Transactional
    public User saveUser(String username, String password, String nickname) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("M#D5+" + MD5Utils.code(password));
        user.setNickname(nickname);
        //默认值
        user.setAvatar("default.jpg");
        user.setPersonalSignature("这个人什么也没说...");
        user.setDonation(0);
        user.setCanSpeak(true);
        user.setAdmin(false);
        user.setRegisterTime(new Date());
        user.setFollowedUserNum(0);
        user.setFollowingUserNum(0);
        user.setPrivacyType(7);

        //生成一个默认收藏夹
        Favorite favorite = new Favorite();
        favorite.setOpen(true);
        favorite.setOwner(user);
        favorite.setTitle("默认收藏夹");
        favoriteServiceImpl.saveFavorite(favorite);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * 更新用户
     */
    @Override
    @Transactional
    public User updateUser(User user) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        //这个u和user虽然id相同，但是已经不是一个对象了
        User u = getUser(user.getId());

        MyBeanUtils.populate(User.class, user, u);

        return userRepository.save(u);
    }

    /**
     * 检查
     */
    @Override
    public User checkRegister(String username, String nickname) {
        User user1 = userRepository.findByUsername(username);
        User user2 = userRepository.findByNickname(nickname);
        //不返回就是返回null
        if(user1 != null)
        {
            return user1;
        } else {
            return user2;
        }
    }

    @Override
    public User getUser(Long userId) {
        Optional<User> opUser = userRepository.findById(userId);
        return opUser.orElse(null);
    }

    @Override
    public User getUser(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    /**
     * 登录
     */
    @Override
    public User checkUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, "M#D5+" + MD5Utils.code(password));
    }

    /**
     * @param donation 某用户的贡献值
     * @return 他前面有几个人
     */
    @Override
    public Long getRank(Integer donation) {
        return userRepository.findAllByDonation(donation) + 1;
    }

    @Override
    public Long countUser() {
        return userRepository.count();
    }

    @Override
    public List<User> listTopUsers(int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "donation");
        Pageable pageable = PageRequest.of(0, size, sort);
        return userRepository.findTop(pageable);
    }
}
