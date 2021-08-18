package com.lrm.service;

import com.lrm.dao.CommentRepository;
import com.lrm.exception.NotFoundException;
import com.lrm.po.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;


    /**
     * 存放迭代找出的所有子代的集合
     */
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 保存评论 如果不是通过回复的方式 那么前端传回parentCommentId默认设置为1
     *
     * @param comment    前端封装好了的Comment对象
     * @param postUser   发布评论的人
     */
    @Override
    @Transactional
    public <T extends Template> Comment saveComment(Comment comment, T t, User postUser) throws NotFoundException {
        //得到前端封装返回的对象的parentId
        Long parentCommentId = comment.getParentCommentId0();


        boolean samePerson = false;

        if (parentCommentId != -1) {
            //有父评论 则是评论问题的评论
            Comment parentComment = getComment(parentCommentId);
            if (parentComment == null) {
                throw new NotFoundException("该父评论不存在");
            }

            //父问题评论数增加
            parentComment.setCommentsNum(parentComment.getCommentsNum() + 1);

            //初始化
            comment.setParentComment(parentComment);
            comment.setReceiveUser(parentComment.getPostUser());
            //父评论是哪类评论 子评论就是哪类
            comment.setAnswer(parentComment.getAnswer());

            //如果评论发布者也是上一级评论发布者 设为已读

            if (postUser.equals(parentComment.getPostUser())) {
                samePerson = true;
            }
        } else {
            //为评论问题的评论

            //在第一行comment.getParentComment中实际上new了一个parentComment对象(初始化id为-1了)
            //但id不能为-1 没有将p...持久化所以会报错 要设成null
            comment.setParentComment(null);
            comment.setReceiveUser(t.getUser());

            //如果评论发布者为问题发布者 设为已读
            if (postUser.equals(t.getUser())) {
                samePerson = true;
            }
        }

        //如果发出评论的人是问题发布者或上一级评论发布者 设为已读
        comment.setLooked(samePerson);
        //如果评论发布者为问题发布者 提供flag adminComment属性为true
        comment.setAdminComment(postUser.equals(t.getUser()));
        //所属问题评论数增加 包含评论下的子评论了
        t.setCommentsNum(t.getCommentsNum() + 1);
        //影响力
        t.setImpact(t.getImpact() + 2);
        if (t instanceof Question) {
            comment.setQuestion((Question) t);
            comment.setQuestionId0(t.getId());
        }
        if (t instanceof Blog) {
            comment.setBlog((Blog) t);
            comment.setBlogId0(t.getId());
        }
        comment.setCreateTime(new Date());
        comment.setLikesNum(0);
        comment.setDisLikesNum(0);
        comment.setCommentsNum(0);
        comment.setHidden(false);
        comment.setPostUser(postUser);
        comment.setPostUserId0(postUser.getId());

        //如果是有效回答 回答者贡献+3 问题影响力+2 否则仅仅问题影响力+2
        if (comment.getAnswer()) {
            postUser.setDonation(postUser.getDonation() + 4);
        }

        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public <T extends Template> void deleteComment(Long commentId, T t, TemplateServiceImpl<T> templateServiceImpl) {

        //复原
        Comment comment = getComment(commentId);
        //父评论
        Comment parentComment = comment.getParentComment();
        //评论发出者
        User postUser = comment.getPostUser();
        //回退
        //TODO： 这个属性还有用么 因为传回前端不是树形的了 是只有两层的数组
        if (parentComment != null) {
            parentComment.setCommentsNum(parentComment.getCommentsNum() - 1);
        }

        t.setCommentsNum(t.getCommentsNum() - 1);

        if (comment.getAnswer()) {
            postUser.setDonation(postUser.getDonation() - 4);
        }
        t.setImpact(t.getImpact() - 2);

        templateServiceImpl.save(t);
        commentRepository.deleteById(commentId);
    }

    @Override
    public Comment getComment(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        return comment.orElse(null);
    }

    /**
     * 得到问题下分级评论（两级）
     *
     * @param isAnswer 是哪一类评论
     * @return comment集合
     */
    @Override
    public List<Comment> listCommentByQuestionId(Long questionId, Boolean isAnswer) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");

        //得到所有第一级评论
        List<Comment> comments = commentRepository.findByQuestionIdAndAnswer(questionId, isAnswer, sort);

        //遍历第一级
        return eachComment(comments);
    }

    /**
     * 遍历所有第一级评论
     *
     * @param comments 第一级评论集合
     * @return 返回评论区的新的评论集合
     */
    private List<Comment> eachComment(List<Comment> comments) {
        //将所有第一级评论保存到commentsView里
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }

        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    private void combineChildren(List<Comment> comments) {
        //遍历所有第一级评论
        for (Comment comment : comments) {

            //得到回复第一级评论的第二级评论
            List<Comment> replys1 = comment.getReplyComments();

            //遍历第二级评论
            for (Comment reply1 : replys1) {

                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }

            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReceiveComments(tempReplys);

            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    private void recursively(Comment comment) {
        //第二级评论添加到临时存放集合
        tempReplys.add(comment);

        //如果第二级评论有子评论
        if (comment.getReplyComments().size() > 0) {
            List<Comment> replys = comment.getReplyComments();

            //遍历第三级评论 添加到临时存放集合
            for (Comment reply : replys) {
                    recursively(reply);
            }
        }
    }

    @Override
    public List<Comment> listCommentByBlogId(Long blogId, Boolean isAnswer) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");

        //得到所有第一级评论
        List<Comment> comments = commentRepository.findByBlogIdAndAnswer(blogId, isAnswer, sort);

        //遍历第一级
        return eachComment(comments);
    }

    /**
     * 得到某问题下的所有评论 计算赞数
     *
     * @param questionId 问题Id
     * @return 未读评论集合
     */
    @Override
    public List<Comment> listAllCommentByQuestionId(Long questionId) {
        return commentRepository.findByQuestionId(questionId);
    }

    /**
     * 得到某博客下的所有评论 计算赞数
     *
     * @param questionId 问题Id
     * @return 未读评论集合
     */
    @Override
    public List<Comment> listAllCommentByBlogId(Long questionId) {
        return commentRepository.findByBlogId(questionId);
    }


    /**
     * 获得未读或已读评论通知
     *
     * @param userId 当前用户Id
     * @return 未读或已读评论集合
     */
    @Override
    public List<Comment> listComments(Long userId, Boolean looked) {
        return commentRepository.findByReceiveUserIdAndLooked(userId, looked);
    }

}
