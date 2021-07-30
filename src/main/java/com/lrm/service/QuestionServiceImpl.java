package com.lrm.service;

import com.lrm.dao.QuestionRepository;
import com.lrm.exception.NotFoundException;
import com.lrm.po.Question;
import com.lrm.po.User;
import com.lrm.util.MarkdownUtils;
import com.lrm.util.MyBeanUtils;
import com.lrm.util.ProcessData;
import com.lrm.vo.QuestionQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TagService tagService;

    /**
     * 保存Question
     * Transactional 一个数据库事务由一条或者多条sql语句构成，它们形成一个逻辑的工作单元。
     * 这些sql语句要么全部执行成功，要么全部执行失败。
     *
     * @param question 前端封装的question
     * @param user     发布Question的user
     * @return 新增了的Question
     */
    @Transactional
    @Override
    public Question saveQuestion(Question question, User user) {
        //将tagIds按顺序重排
        question.setTagIds(tagService.listTagIdsFromSmallToBig(question.getTagIds()));
        //时间是date对象所以新增的时候需要初始化 否则为null;
        question.setCreateTime(new Date());
        question.setNewCommentedTime(new Date());
        question.setView(0);
        //初始化点赞数、点踩数、评论数为0
        question.setLikesNum(0);
        question.setCommentsNum(0);
        question.setDisLikesNum(0);
        //默认为不隐藏
        question.setHidden(false);
        //根据发布Question人的贡献初始化Question的影响力
        user.setDonation(user.getDonation() + 2);
        question.setImpact(user.getDonation());
        return questionRepository.save(question);
    }

    @Override
    @Transactional
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }


    /**
     * 管理页更新Question
     *
     * @param question 需要更新的question
     * @return 被更新了的question
     */
    @Override
    @Transactional
    public Question updateQuestion(Question question)
    {
        Question q = getQuestion(question.getId());
        BeanUtils.copyProperties(question, q, MyBeanUtils.getNullPropertyNames(question));
        return questionRepository.save(q);
    }

    @Override
    @Transactional
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public Question getQuestion(Long id) {
        Optional<Question> opQuestion = questionRepository.findById(id);
        return opQuestion.orElse(null);
    }

    @Override
    public Long countQuestion() {
        return questionRepository.count();
    }

    /**
     * 根据用户Id查询用户发布的Question数量
     */
    @Override
    public Long countQuestionByUser(Long userId) {
        return questionRepository.countAllByUserId(userId);
    }

    /**
     * 管理页根据userid、标题（标签查询未做）搜索 前端传入QuestionQuery对象和userId
     *
     * @param pageable 分页对象
     * @param question 查询条件
     * @param nickname 查询的用户昵称
     * @return 查询结果
     */
    @Override
    public Page<Question> listQuestionPlusNickname(Pageable pageable, QuestionQuery question, String nickname) {
        return questionRepository.findAll((root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            //""是空字节，而不是null 下面这种写法 而不是Question.getTitle().equals("")是防止它是null 从而造成空指针异常
            if (question.getTitle() != null && !"".equals(question.getTitle())) {
                predicates.add(cb.like(root.get("title"), "%" + question.getTitle() + "%"));
            }
            if (nickname != null && !"".equals(nickname)) {
                Join<Object, Object> join = root.join("user");
                predicates.add(cb.equal(join.get("nickname"), nickname));
            }

            String tagIds = question.getTagIds();
            if (tagIds != null && !"".equals(tagIds)) {
                String newTagIds = tagService.listTagIdsFromSmallToBig(tagIds);

                predicates.add(cb.like(root.get("tagIds"), "%" + newTagIds + "%"));
            }

            cq.where(predicates.toArray(new Predicate[0]));
            return null;
        }, pageable);
    }

    /**
     * markdown转换
     *
     * @param questionId QuestionId
     * @return 转换过的Question
     */
    @Override
    public Question getAndConvert(Long questionId) {
        Question question = getQuestion(questionId);
        if (question == null) {
            throw new NotFoundException("该Question不存在");
        }
        //每多1次浏览，Question影响力+2
        question.setView(question.getView()+1);
        question.setImpact(question.getImpact() + 2);
        questionRepository.save(question);
        Question q = new Question();
        BeanUtils.copyProperties(question, q);
        String content = q.getContent();
        q.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return q;
    }

    /**
     * 通过直接搜索标题查询
     *
     * @param query 查询条件
     * @return 查询结果
     */
    @Override
    public Page<Question> listQuestion(String query, Pageable pageable) {
        return questionRepository.findByQuery(query, pageable);
    }

    /**
     * 列出所有Question
     */
    @Override
    public Page<Question> listQuestion(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    /**
     * 列出某个tagId对应的Question集合
     *
     * @param tagId 标签Id
     * @return Question集合
     */
    @Override
    public Set<Question> listQuestion(Long tagId) {
        return new HashSet<>(questionRepository.findAll((Specification<Question>) (root, cq, cb) -> {
            Join<Object, Object> join = root.join("tags");
            return cb.equal(join.get("id"), tagId);
        }));
    }

    /**
     * 查找收藏夹中的内容
     *
     * @param favoriteId 收藏夹Id
     * @param pageable   排序方式
     * @return Question集合
     */
    @Override
    public Page<Question> listQuestionByFavoriteId(Long favoriteId, Pageable pageable) {
        return questionRepository.findAll((Specification<Question>) (root, cq, cb) -> {
            Join<Object, Object> join = root.join("favorites");
            return cb.equal(join.get("id"), favoriteId);
        }, pageable);
    }

    /**
     * 返回最新评论在三天之内的 影响力为前size个的Question
     *
     * @param size 需要的个数
     * @return 满足条件的Question集合
     */
    @Override
    public List<Question> listImpactQuestionTop(Integer size) {

        List<Order> orders = new ArrayList<>();
        Order order1 = new Order(Sort.Direction.DESC, "createTime");
        Order order2 = new Order(Sort.Direction.DESC, "impact");
        orders.add(order1);
        orders.add(order2);
        Pageable pageable = PageRequest.of(0, size, Sort.by((orders)));
        List<Question> questions = questionRepository.findTop(pageable);

        if(questions.size() > size-1)
        {
            return questions.subList(0, size - 1);
        }
        return questions;
        }


    /**
     * 归档 按日期顺序返回自己发布的Question
     */
    @Override
    public Map<String, Map<String, List<Question>>> archivesQuestion(Long userId) {
        List<String> years = questionRepository.findGroupYear(userId);
        years = ProcessData.removeDuplicateElement(years);
        //定制排序 让年份逆序 2021→2020→2019
        Map<String, Map<String, List<Question>>> map = new TreeMap<>(Comparator.reverseOrder());
        for (String year : years) {
            List<String> months = questionRepository.findGroupMonthByYear(year, userId);
            months = ProcessData.removeDuplicateElement(months);
            //让月份逆序 12→11→10
            Map<String, List<Question>> treeMap = new TreeMap<>(Comparator.reverseOrder());
            for (String month : months) {
                treeMap.put(month, questionRepository.findByYearAndMonth(year, month, userId));
            }
            map.put(year, treeMap);
        }
        return map;
    }
}
