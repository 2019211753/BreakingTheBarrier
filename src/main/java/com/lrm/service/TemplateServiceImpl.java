package com.lrm.service;

import com.lrm.dao.TemplateRepository;
import com.lrm.enumeration.DonationGrow;
import com.lrm.enumeration.ImpactGrow;
import com.lrm.po.Template;
import com.lrm.po.User;
import com.lrm.util.DataStructureUtils;
import com.lrm.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * 实现泛型方法 简化增删改查的代码
 * QuestionServiceImpl和BlogServiceImpl的公有父类
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-08-08
 */
public abstract class TemplateServiceImpl<T extends Template>  {
    @Autowired
    TagService tagService;

    /**
     * 模板设计模式
     *
     * @return 实际指向BlogRepository或QuestionRepository的TemplateRepository对象
     */
    public abstract TemplateRepository<T> getTemplateRepository();

    /**
     * 保存
     * Transactional 一个数据库事务由一条或者多条sql语句构成，它们形成一个逻辑的工作单元。
     * 这些sql语句要么全部执行成功，要么全部执行失败。
     *
     * @param t 前端封装的Question或Blog
     * @param user     发布者user
     * @return 新增了的Question或Blog
     */
    @Transactional
    public T save(T t, User user) {
        TemplateRepository<T> repository = getTemplateRepository();
        //将tagIds按顺序重排
        t.setTagIds(tagService.listTagIdsFromSmallToBig(t.getTagIds()));
        t.setTags(tagService.listTags(t.getTagIds()));
        //关联用户相关
        t.setUser(user);
        //时间是date对象所以新增的时候需要初始化 否则为null;
        t.setCreateTime(new Date());
        t.setNewCommentedTime(new Date());
        t.setView(0);
        //初始化点赞数、点踩数、评论数为0
        t.setLikesNum(0);
        t.setCommentsNum(0);
        t.setDisLikesNum(0);
        t.setCollectedNum(0);
        //默认为不隐藏
        t.setHidden(false);
        //根据发布t人的贡献初始化t的影响力
        user.setDonation(user.getDonation() + DonationGrow.POST_TEMPLATE.getGrow());
        t.setImpact(user.getDonation());
        return repository.save(t);
    }

    @Transactional
    public T save(T t) {
        TemplateRepository<T> repository = getTemplateRepository();

        return repository.save(t);
    }

    @Transactional
    public void deleteById(Long id) {
        TemplateRepository<T> repository = getTemplateRepository();

        repository.deleteById(id);

    }

    @Transactional
    public T update(T newT) {
        TemplateRepository<T> repository = getTemplateRepository();

        T oldT = getById(newT.getId());
        BeanUtils.copyProperties(newT, oldT, MyBeanUtils.getNullPropertyNames(newT));
        return repository.save(oldT);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public T getById(Long id) {
        TemplateRepository<T> repository = getTemplateRepository();

        Optional<T> opT = repository.findById(id);
        return opT.orElse(null);
    }

    public Long countAll() {
        TemplateRepository<T> repository = getTemplateRepository();

        return repository.count();
    }

    public Long countAllByUserId(Long userId) {
        TemplateRepository<T> repository = getTemplateRepository();

        return repository.countAllByUserId(userId);
    }

    public Page<T> listAll(Pageable pageable) {
        TemplateRepository<T> repository = getTemplateRepository();

        return repository.findAll(pageable);
    }

    public Set<T> listByTagId(Long tagId) {
        TemplateRepository<T> repository = getTemplateRepository();

        return new HashSet<>(repository.findAll((Specification<T>) (root, cq, cb) -> {
            Join<Object, Object> join = root.join("tags");
            return cb.equal(join.get("id"), tagId);
        }));
    }

    public Page<T> listByFavoriteId(Long favoriteId, Pageable pageable) {
        TemplateRepository<T> repository = getTemplateRepository();

        return repository.findAll((Specification<T>) (root, cq, cb) -> {
            Join<Object, Object> join = root.join("favorites");
            return cb.equal(join.get("id"), favoriteId);
        }, pageable);
    }

    public Page<T> listByQuery(String query, Pageable pageable) {
        TemplateRepository<T> repository = getTemplateRepository();

        return repository.findByQuery(query, pageable);
    }

    /**
     * 多重条件查询
     *
     * @param pageable 分页对象
     * @param queryMap 查询条件
     * @return 查询结果
     */
    public Page<T> listByQueryAndTagIdsAndNickname(Pageable pageable, Map<String, String> queryMap) {
        TemplateRepository<T> repository = getTemplateRepository();

        String query = queryMap.get("query");
        String nickname = queryMap.get("nickname");
        String tagIds = queryMap.get("tagIds");

        Page<T> pages = repository.findAll((root, cq, cb) -> {
            //或查询
            List<Predicate> listOr = new ArrayList<>();
            //""是空字节，而不是null 下面这种写法 而不是Question.getTitle().equals("")是防止它是null 从而造成空指针异常
            if (query != null && !"".equals(query)) {
                listOr.add(cb.like(root.get("title"), "%" + query + "%"));
                listOr.add(cb.like(root.get("description"), "%" + query + "%"));
                listOr.add(cb.like(root.get("content"), "%" + query + "%"));
            }
            //or查询加入查询条件
            Predicate predicateOr = cb.or(listOr.toArray(new Predicate[0]));

            //与查询
            List<Predicate> listAnd = new ArrayList<>(4);

            if (nickname != null && !"".equals(nickname)) {
                Join<Object, Object> join = root.join("user");
                listAnd.add(cb.equal(join.get("nickname"), nickname));
            }

            if (tagIds != null && !"".equals(tagIds)) {
                String newTagIds = tagService.listTagIdsFromSmallToBig(tagIds);
                listAnd.add(cb.like(root.get("tagIds"), "%" + newTagIds + "%"));
            }

            listAnd.addAll(setMoreCriteria(cb, root));

            if (root.get("open") != null) {
                listAnd.add(cb.equal(root.get("open"), false));
            }
            //and查询加入查询条件
            Predicate predicateAnd = cb.and(listAnd.toArray(new Predicate[0]));

            cq.where(predicateOr, predicateAnd);
            return null;
        }, pageable);

        //TODO: 待测试
        //不合法的数据：如需要查询的tagIds为1,2,3 但上面会把 11,2,31也查询出来
        //特殊数据 1 会出现21 12 就查询每个id 如果包含1就合法
        ArrayList<T> contents = new ArrayList<>(pages.getContent());
label:  for (T t : contents)
        {
            //需要查询的tagIds的第一个和最后一个,出现的位置
            int firstCom = tagIds.indexOf(",");
            int lastCom = tagIds.lastIndexOf(",");

            //如果没有, 对查询结果的tagIds进行遍历 如果没有要查询的id 则为不合法的数据 移除 如果有 保留
            if (firstCom == lastCom && firstCom == -1) {
                String[] ids = tagIds.split(",");
                for (String id : ids) {
                    if (tagIds.equals(id)) {
                        continue label;
                    }
                }
                pages.getContent().remove(t);
            } else {
                String firstTagId = tagIds.substring(0, firstCom);
                String lastTagId = tagIds.substring(lastCom + 1);
                int firstCom_ = t.getTagIds().indexOf(",");
                String firstTagId_ = t.getTagIds().substring(0, firstCom_);
                int lastCom_ = t.getTagIds().lastIndexOf(",");
                String lastTagId_ = t.getTagIds().substring(lastCom_ + 1);

                //如果第一个标签和最后一个标签与被查询的标签对不上号 删去
                if (!firstTagId.equals(firstTagId_) || !lastTagId.equals(lastTagId_)) {
                    pages.getContent().remove(t);
                }
            }
        }

        return pages;
    }

    public List<Predicate> setMoreCriteria(CriteriaBuilder query, Root<T> root) {
        return null;
    }

    public T getAndConvert(Long id, T frontT) {
        TemplateRepository<T> repository = getTemplateRepository();

        //后端数据库查询得到的T对象
        T backT = getById(id);
        //每多1次浏览，影响力+
        backT.setView(backT.getView()+1);
        backT.setImpact(backT.getImpact() + ImpactGrow.CLICKED.getGrow());
        repository.save(backT);
        BeanUtils.copyProperties(backT, frontT);
        String content = backT.getContent();
        frontT.setDetails(content);
        return frontT;
    }

    /**
     * 返回最新评论在三天之内的 影响力为前size个的Question或Blog
     *
     * @param size 需要的个数
     * @return 满足条件的Question或Blog集合
     */
    public List<T> listImpactTop(Integer size) {
        TemplateRepository<T> repository = getTemplateRepository();

        List<Sort.Order> orders = new ArrayList<>();
        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC, "createTime");
        Sort.Order order2 = new Sort.Order(Sort.Direction.DESC, "impact");
        orders.add(order1);
        orders.add(order2);
        Pageable pageable = PageRequest.of(0, size, Sort.by((orders)));
        List<T> ts = repository.findTop(pageable);

        if(ts.size() > size - 1)
        {
            return ts.subList(0, size - 1);
        }
        return ts;
    }

    public Map<String, Map<String, List<T>>> archivesByUserId(Long userId) {
        TemplateRepository<T> repository = getTemplateRepository();

        List<String> years = repository.findGroupYear(userId);
        years = DataStructureUtils.removeDuplicateElement(years);
        //定制排序 让年份逆序 2021→2020→2019
        Map<String, Map<String, List<T>>> map = new TreeMap<>(Comparator.reverseOrder());
        for (String year : years) {
            List<String> months = repository.findGroupMonthByYear(year, userId);
            months = DataStructureUtils.removeDuplicateElement(months);
            //让月份逆序 12→11→10
            Map<String, List<T>> treeMap = new TreeMap<>(Comparator.reverseOrder());
            for (String month : months) {
                treeMap.put(month, repository.findByYearAndMonth(year, month, userId));
            }
            map.put(year, treeMap);
        }
        return map;
    }
}
