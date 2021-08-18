package com.lrm.web;

import com.lrm.exception.FailedOperationException;
import com.lrm.exception.NoPermissionException;
import com.lrm.exception.NotFoundException;
import com.lrm.po.Blog;
import com.lrm.po.Favorite;
import com.lrm.po.Question;
import com.lrm.po.Template;
import com.lrm.service.BlogServiceImpl;
import com.lrm.service.FavoriteServiceImpl;
import com.lrm.service.QuestionServiceImpl;
import com.lrm.service.TemplateServiceImpl;
import com.lrm.util.TokenInfo;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/***
 * @author 山水夜止
 * @version 1.0
 * @date 2021-08-08
 */
@RestController
public class FavoriteController {

    @Autowired
    private QuestionServiceImpl questionServiceImpl;

    @Autowired
    private FavoriteServiceImpl favoriteServiceImpl;

    @Autowired
    private BlogServiceImpl blogServiceImpl;


    /**
     * 收藏或移除问题
     *
     * @param questionId 问题Id
     * @param favoriteId 收藏夹Id
     * @param request    获取当前用户Id
     * @param pageable   为了方便调用方法的 不想再造了
     * @return 添加成功或失败抛出异常
     */
    @GetMapping("favorite/{favoriteId}/modify/question/{questionId}")
    public Result modifyQuestionInFavorite(@PathVariable Long favoriteId, @PathVariable Long questionId,
                                           HttpServletRequest request, @PageableDefault Pageable pageable) {
        Favorite favorite = checkFavorite(favoriteId, request);

        Question question = questionServiceImpl.getById(questionId);
        if (question == null) {
            throw new NotFoundException("未查询到该问题");
        }

        return modifyFavorite(question, questionServiceImpl, favorite, pageable);
    }

    @GetMapping("favorite/{favoriteId}/modify/blog/{blogId}")
    public Result modifyBlogInFavorite(@PathVariable Long favoriteId, @PathVariable Long blogId,
                                           HttpServletRequest request, @PageableDefault Pageable pageable) {
        Favorite favorite = checkFavorite(favoriteId, request);

        Blog blog = blogServiceImpl.getById(blogId);
        if (blog == null) {
            throw new NotFoundException("未查询到该博客");
        }

        return modifyFavorite(blog, blogServiceImpl, favorite, pageable);
    }

    public Favorite checkFavorite(@PathVariable Long favoriteId, HttpServletRequest request) {
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

        if (favorite.getFavoriteQuestions().contains(t) || favorite.getFavoriteBlogs().contains(t)) {

            if (favorite.getSize() == Favorite.getMAXSIZE()) {
                throw new FailedOperationException("收藏夹已满");
            }

            favoriteServiceImpl.add(favorite, t);

            if (templateServiceImpl.listByFavoriteId(favorite.getId(), pageable)
                    .getContent().contains(t)) {
                return new Result(null, "添加成功");
            } else {
                throw new FailedOperationException("添加失败");
            }
        } else {
            favoriteServiceImpl.remove(favorite, t);

            if (!templateServiceImpl.listByFavoriteId(favorite.getId(), pageable)
                    .getContent().contains(t)) {
                return new Result(null, "删除成功");
            } else {
                throw new FailedOperationException("删除失败");
            }
        }
    }
}
