package com.lrm.web.customer;

import com.lrm.enumeration.DonationGrow;
import com.lrm.enumeration.ImpactGrow;
import com.lrm.exception.FailedOperationException;
import com.lrm.exception.IllegalParameterException;
import com.lrm.exception.NoPermissionException;
import com.lrm.exception.NotFoundException;
import com.lrm.po.*;
import com.lrm.service.*;
import com.lrm.util.TokenInfo;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理收藏夹
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@RequestMapping("/customer")
@RestController
public class CustomerFavoriteController {

    @Autowired
    private FavoriteServiceImpl favoriteServiceImpl;

    @Autowired
    private QuestionServiceImpl questionServiceImpl;

    @Autowired
    private BlogServiceImpl blogServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * 创建收藏夹
     *
     * @param favorite      前端传回favorite对象
     * @param bindingResult 处理异常
     * @param request       获取当前userId
     * @return 创建后的收藏夹
     */
    @PostMapping("/favorite/create")
    public Result createFavorite(@RequestBody @Valid Favorite favorite, BindingResult bindingResult, HttpServletRequest request) {
        Long userId = TokenInfo.getCustomUserId(request);
        Map<String, Object> hashMap = new HashMap<>(1);

        //异常处理
        if (bindingResult.hasErrors()) {
            throw new IllegalParameterException(IllegalParameterException.getMessage(bindingResult));
        }

        Long id;
        if ((id = favorite.getId()) != null) {
            if (favoriteServiceImpl.getFavoriteById(id) != null) {
                throw new FailedOperationException("该收藏夹已存在");
            }
        }
        if (favoriteServiceImpl.getFavoriteByTitle(favorite.getTitle(), userId) != null) {
            throw new FailedOperationException("已有同名收藏夹");
        }

        User owner = userServiceImpl.getUser(userId);
        favorite.setOwner(owner);
        Favorite favorite1 = favoriteServiceImpl.saveFavorite(favorite);
        if (favorite1 != null) {
            hashMap.put("favorite", favorite1);
            return new Result(hashMap, "发布成功");
        } else {
            throw new FailedOperationException("发布失败");
        }
    }

    /**
     * 删除收藏夹
     *
     * @param favoriteId 收藏夹的Id
     * @param request    获取当前用户Id
     * @return 删除成功的信息或抛出异常
     */
    @GetMapping("/favorite/{favoriteId}/delete")
    public Result deleteFavorite(@PathVariable Long favoriteId, HttpServletRequest request) {
        Long customUserId = TokenInfo.getCustomUserId(request);
        Favorite favorite = favoriteServiceImpl.getFavoriteById(favoriteId);
        //如果收藏夹不存在 抛出404异常
        if (favorite == null) {
            throw new NotFoundException("未查询到该收藏夹");
            //如果收藏夹存在 且执行该方法的用户不是收藏夹所有者 抛出无权限异常
        } else if (!favorite.getOwner().getId().equals(customUserId)) {
            throw new NoPermissionException("你无权删除该收藏夹");
        }

        favoriteServiceImpl.deleteFavoriteById(favoriteId);
        if (favoriteServiceImpl.getFavoriteById(favoriteId) == null) {
            return new Result(null, "删除成功");
        } else {
            throw new FailedOperationException("删除失败");
        }
    }

    /**
     * 更新收藏夹名称和是否公开
     * @param favorite0 前端封装好的收藏夹
     * @param request  获取Id
     * @return 新收藏夹
     */
    @PostMapping("/favorite/edit")
    public Result editFavorite(Favorite favorite0, HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(1);

        Long customUserId = TokenInfo.getCustomUserId(request);
        Long favoriteId = favorite0.getId();
        String title = favorite0.getTitle();

        //如果收藏夹不存在 抛出404异常
        if (favoriteId == null) {
            throw new NotFoundException("未查询到该收藏夹");
        }
        //查询到数据库中的对应该Id的收藏夹
        Favorite favorite = favoriteServiceImpl.getFavoriteById(favoriteId);
        //如果收藏夹不存在 抛出404异常
        if (favorite == null) {
            throw new NotFoundException("未查询到该收藏夹");
            //如果收藏夹存在
        } else {
            //执行该方法的用户不是收藏夹所有者 抛出无权限异常
            if (!favorite.getOwner().getId().equals(customUserId)) {
                throw new NoPermissionException("你无权编辑该收藏夹");
            }
            //标题就是null或者是""
            if ("".equals(title) || title == null) {
                throw new IllegalParameterException("请输入收藏夹标题");
            }
            //新标题已存在了 而且不是他原来的标题（即改的结果不是跟原来的一样）
            //查询到数据库中的对应该title的收藏夹
            Favorite favorite1 = favoriteServiceImpl.getFavoriteByTitle(favorite.getTitle(), customUserId);
            if (favorite1 != null && !favorite1.getId().equals(favoriteId)) {
                throw new FailedOperationException("已有同名收藏夹");
            }
        }

        favoriteServiceImpl.updateFavorite(favorite0, favorite);

        hashMap.put("favorite", favorite);
        return new Result(hashMap, "修改成功");
    }

    /**
     * 展示用户的所有收藏夹
     *
     * @param request 返回当前用户Id
     * @return 用户的所有收藏夹
     */
    @GetMapping("/favorites")
    public Result showFavorites(HttpServletRequest request) {
        Long customerUserId = TokenInfo.getCustomUserId(request);
        Map<String, Object> hashMap = new HashMap<>(1);
        hashMap.put("favorites", favoriteServiceImpl.getFavoritesByUserId(customerUserId));
        return new Result(hashMap, "");
    }

    /**
     * 展示某收藏夹的内容
     *
     * @param favoriteId 收藏夹Id
     * @param pageable   排序方式
     * @return 收藏夹下的问题
     */
    @GetMapping("/favorite/{favoriteId}")
    public Result showQuestions(@PathVariable Long favoriteId,
                                @PageableDefault(size = 7, direction = Sort.Direction.DESC) Pageable pageable,
                                HttpServletRequest request) {
        Long customerUserId = TokenInfo.getCustomUserId(request);
        Map<String, Object> hashMap = new HashMap<>(2);

        Favorite favorite = favoriteServiceImpl.getFavoriteById(favoriteId);
        if (favorite == null) {
            throw new NotFoundException("未查询到该收藏夹");
        } else {
            if (!favorite.getOwner().getId().equals(customerUserId)) {
                throw new NoPermissionException("你无权访问该收藏夹");
            }
        }

        hashMap.put("questions", questionServiceImpl.listByFavoriteId(favoriteId, pageable));
        hashMap.put("blogs", blogServiceImpl.listByFavoriteId(favoriteId, pageable));
        return new Result(hashMap, "");
    }

    /**
     * 收藏或移除问题
     *
     * @param questionId 问题Id
     * @param favoriteId 收藏夹Id
     * @param request    获取当前用户Id
     * @param pageable   为了方便调用方法的 不想再造了
     * @return 添加成功或失败抛出异常
     */
    @GetMapping("/favorite/{favoriteId}/modify/question/{questionId}")
    public Result modifyQuestionInFavorite(@PathVariable Long favoriteId, @PathVariable Long questionId,
                                           HttpServletRequest request, @PageableDefault Pageable pageable) {
        Favorite favorite = checkFavorite(favoriteId, request);

        Question question = questionServiceImpl.getById(questionId);
        if (question == null) {
            throw new NotFoundException("未查询到该问题");
        }

        return modifyFavorite(question, questionServiceImpl, favorite, pageable);
    }

    @GetMapping("/favorite/{favoriteId}/modify/blog/{blogId}")
    public Result modifyBlogInFavorite(@PathVariable Long favoriteId, @PathVariable Long blogId,
                                       HttpServletRequest request, @PageableDefault Pageable pageable) {
        Favorite favorite = checkFavorite(favoriteId, request);

        Blog blog = blogServiceImpl.getById(blogId);
        if (blog == null) {
            throw new NotFoundException("未查询到该博客");
        }

        return modifyFavorite(blog, blogServiceImpl, favorite, pageable);
    }

    public Favorite checkFavorite(Long favoriteId, HttpServletRequest request) {
        Long customUserId = TokenInfo.getCustomUserId(request);

        Favorite favorite = favoriteServiceImpl.getFavoriteById(favoriteId);
        if (favorite == null) {
            throw new NotFoundException("未查询到该收藏夹");
        }

        if (!favorite.getOwner().getId().equals(customUserId)) {
            throw new NoPermissionException("你无权操作该收藏夹");
        }
        return favorite;
    }

    <T extends Template> Result modifyFavorite(T t, TemplateServiceImpl<T> templateServiceImpl, Favorite favorite, Pageable pageable) {
        Map<String, Object> hashMap = new HashMap<>(2);
        if (!favorite.getFavoriteQuestions().contains(t) && !favorite.getFavoriteBlogs().contains(t)) {

            if (favorite.getSize() == Favorite.getMAXSIZE()) {
                throw new FailedOperationException("收藏夹已满");
            }

            favoriteServiceImpl.add(favorite, t);
            t.setImpact(t.getImpact() + ImpactGrow.COLLECTED.getGrow());
            t.getUser().setDonation(t.getUser().getDonation() + DonationGrow.TEMPLATE_COLLECTED.getGrow());

            templateServiceImpl.save(t);

            hashMap.put("collectedNum", t.getCollectedNum());
            hashMap.put("collected", true);
            return new Result(hashMap, "添加成功");
        } else {
            favoriteServiceImpl.remove(favorite, t);
            t.setImpact(t.getImpact() - ImpactGrow.COLLECTED.getGrow());
            templateServiceImpl.save(t);

            hashMap.put("collectedNum", t.getCollectedNum());
            hashMap.put("collected", false);
            return new Result(hashMap, "删除成功");
        }
    }
}
