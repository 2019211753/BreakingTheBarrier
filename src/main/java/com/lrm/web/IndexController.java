package com.lrm.web;

import com.lrm.exception.FailedOperationException;
import com.lrm.exception.NoPermissionException;
import com.lrm.exception.NotFoundException;
import com.lrm.po.*;
import com.lrm.service.*;
import com.lrm.util.TokenInfo;
import com.lrm.vo.Magic;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 乱七八糟的
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@RestController
public class IndexController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikesService likesService;

    @Autowired
    private DisLikesService disLikesService;

    @Autowired
    private FavoriteService favoriteService;

    /**
     * 怎么显示有没有点过赞呢？现在不太明白...只能用计算力代替了
     *
     * @param request  用于得到当前userId 处理当前用户点没点过赞的
     * @param pageable 分页
     * @return 返回推荐问题、全部问题
     */
    @GetMapping("/")
    public Result index(@PageableDefault(size = 7, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(3);

        Page<Question> page = questionService.listQuestion(pageable);

        for (Question question : page) {

            //得到发布问题的人
            User postUser = question.getUser();
            //这里到底要不要用计算力代替空间还要考虑
            question.setAvatar(postUser.getAvatar());
            question.setNickname(postUser.getNickname());
        }
        hashMap.put("pages", page);
        hashMap.put("impactQuestions", questionService.listImpactQuestionTop(Magic.RECOMMENDED_QUESTIONS_SIZE));
        return new Result(hashMap, "");
    }

    /**
     * 按输入搜索标题/内容
     *
     * @param pageable 分页
     * @param query    查询条件
     * @return 查询结果、查询条件
     */
    @PostMapping("/searchQuestions")
    public Result search(@PageableDefault(size = 1000, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         String query) {
        Map<String, Object> hashMap = new HashMap<>(2);

        Page<Question> questions = questionService.listQuestion("%" + query + "%", pageable);

        for (Question question : questions.getContent()) {
            //得到发布问题的人
            User postUser = question.getUser();

            question.setAvatar(postUser.getAvatar());
            question.setNickname(postUser.getNickname());
        }
        //mysql语句 模糊查询的格式 jpa不会帮处理string前后有没有%的
        hashMap.put("pages", questions);

        //还要传回 保证在新的查询页面 查询框中也有自己之前查询的条件的内容
        hashMap.put("query", query);
        return new Result(hashMap, "");
    }

    /**
     * 问题内容展示
     *
     * @param questionId 问题Id
     * @return 问题的内容
     */
    @GetMapping("/question/{questionId}")
    public Result question(@PathVariable Long questionId, HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(1);
        Long userId = TokenInfo.getCustomUserId(request);

        if (questionService.getQuestion(questionId) == null) {
            throw new NotFoundException("未查询到该问题");
        }
        //返回markdown格式
        Question question = questionService.getAndConvert(questionId);

        //可简化如下 但为逻辑清晰这样写
        //question.setApproved(likesService.getLikes(userService.getUser(userId), question) != null);

        if (likesService.getLikes(userService.getUser(userId), question) != null) {
            question.setApproved(true);
        } else {
            question.setApproved(false);
        }

        if (disLikesService.getDisLikes(userService.getUser(userId), question) != null) {
            question.setDisapproved(true);
        } else {
            question.setDisapproved(false);
        }

        for (Favorite favorite : favoriteService.getFavoritesByUserId(userId)) {
            if (favorite.getFavoriteQuestions().contains(question)) {
                question.setCollected(true);
            } else {
                question.setCollected(false);
            }
        }

        question.setAvatar(question.getUser().getAvatar());
        question.setNickname(question.getUser().getNickname());

        hashMap.put("question", question);
        return new Result(hashMap, "");
    }

    /**
     * 点赞/取消赞
     *
     * @param request    获得执行赞动作的用户id
     * @param questionId 问题Id
     */
    @GetMapping("/question/{questionId}/approve")
    public void approve(@PathVariable Long questionId, HttpServletRequest request) {
        Long postUserId = TokenInfo.getCustomUserId(request);

        Question question = questionService.getQuestion(questionId);
        if (question == null) {
            throw new NotFoundException("未查询到该问题");
        }

        //得到消息接受双方
        User postUser = userService.getUser(postUserId);
        User receiveUser = question.getUser();

        //若点过踩 取消点踩
        DisLikes dislikes = disLikesService.getDisLikes(postUser, question);
        if (dislikes != null) {
            disLikesService.deleteDisLikes(dislikes);

            hideQuestion(question, -1);
        }

        //如果存在点赞对象 就删除 即取消点赞 否则点赞
        Likes likes = likesService.getLikes(postUser, question);
        if (likes != null) {
            likesService.deleteLikes(likes);

            dealLikes(question, receiveUser, -1);
        } else {
            Likes likes1 = new Likes();

            //初始化
            likes1.setLikeQuestion(true);
            likes1.setLikeComment(false);
            likes1.setQuestion(question);

            likesService.saveLikes(likes1, postUser, receiveUser);

            //处理部分属性
            dealLikes(question, receiveUser, 1);
        }
    }

    /**
     * 点踩/取消踩
     *
     * @param request    获取执行踩动作的用户id
     * @param questionId 问题Id
     */
    @GetMapping("/question/{questionId}/disapprove")
    public void disapprove(@PathVariable Long questionId, HttpServletRequest request) {
        Long postUserId = TokenInfo.getCustomUserId(request);

        Question question = questionService.getQuestion(questionId);
        if (question == null) {
            throw new NotFoundException("未查询到该问题");
        }

        User postUser = userService.getUser(postUserId);
        User receiveUser = question.getUser();

        //若点过赞 取消点赞 贡献值复原
        Likes likes = likesService.getLikes(postUser, question);
        if (likes != null) {
            likesService.deleteLikes(likes);
            dealLikes(question, receiveUser, -1);
        }

        //若点过踩 取消点踩 否则点踩
        DisLikes dislikes = disLikesService.getDisLikes(postUser, question);
        if (dislikes != null) {
            disLikesService.deleteDisLikes(dislikes);

            hideQuestion(question, -1);
        } else {
            DisLikes dislikes1 = new DisLikes();

            dislikes1.setDislikeQuestion(true);
            dislikes1.setDislikeComment(false);
            dislikes1.setQuestion(question);

            disLikesService.saveDisLikes(dislikes1, postUser);
            hideQuestion(question, 1);
        }
    }

    void dealLikes(Question question, User receiveUser, int p) {
        //不明白为什么把他们放在saveLikes之前就可以update
        question.setLikesNum(question.getLikesNum() + p * 1);

        //提问者贡献值对问题影响力+-8 (取消）点赞本身+-2
        question.setImpact(question.getImpact() + p * 2 + p * 8);

        //问题被（取消）点赞 提问者贡献值+-2
        receiveUser.setDonation(receiveUser.getDonation() + p * 2);

        questionService.saveQuestion(question);
        userService.saveUser(receiveUser);
    }

    void hideQuestion(Question question, int p) {
        question.setDisLikesNum(question.getDisLikesNum() + p);

        //被踩到一定程度隐藏评论
        if ((question.getDisLikesNum() >= Magic.HIDE_STANDARD1) & (question.getLikesNum() <= Magic.HIDE_STANDARD2 * question.getDisLikesNum())) {
            question.setHidden(true);
        } else {
            question.setHidden(false);
        }
        questionService.saveQuestion(question);
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
    @GetMapping("favorite/{favoriteId}/modify/{questionId}")
    public Result modifyQuestionInFavorite(@PathVariable Long favoriteId, @PathVariable Long questionId,
                                           HttpServletRequest request, @PageableDefault Pageable pageable) {
        Long customUserId = TokenInfo.getCustomUserId(request);

        Favorite favorite = favoriteService.getFavoriteById(favoriteId);
        if (favorite == null) {
            throw new NotFoundException("未查询到该收藏夹");
        }

        Question question = questionService.getQuestion(questionId);
        if (question == null) {
            throw new NotFoundException("未查询到该问题");
        }

        if (!favorite.getOwner().getId().equals(customUserId)) {
            throw new NoPermissionException("你无权操作该收藏夹");
        }

        if (favorite.getFavoriteQuestions().contains(question)) {

            if (favorite.getSize() == Favorite.getMAXSIZE()) {
                throw new FailedOperationException("收藏夹已满");
            }

            favoriteService.addQuestion(favorite, question);

            if (questionService.listQuestionByFavoriteId(favoriteId, pageable)
                    .getContent().contains(question)) {
                return new Result(null, "添加成功");
            } else {
                throw new FailedOperationException("添加失败");
            }
        } else {
            favoriteService.removeQuestion(favorite, question);

            if (!questionService.listQuestionByFavoriteId(favoriteId, pageable)
                    .getContent().contains(question)) {
                return new Result(null, "删除成功");
            } else {
                throw new FailedOperationException("删除失败");
            }
        }

    }
}
