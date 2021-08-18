package com.lrm.dao;

import com.lrm.po.Template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Question和Blog公用的接口
 * 为了告诉JPA不要创建对应接口的bean对象 就在类上加注解@NoRepositoryBean
 * 这样spring容器中就不会有TemplateRepository接口的bean对象
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-08-08
 */
@NoRepositoryBean
public interface TemplateRepository<T extends Template> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
    /**
     * 查询标题或正文含有query的博客/问题
     *
     * @param query    查询条件 按发布时间顺序查询
     * @param pageable 分页对象
     * @return 查询结果
     */
    @Query("select t from #{#entityName} t where t.title like ?1 or t.content like ?1 or t.description like ?1")
    Page<T> findByQuery(String query, Pageable pageable);

    /**
     * 将所有博客/问题按时间的年分割
     *
     * @param userId 当前用户Id
     * @return  顺序返回年份List集合
     */
    @Query("select function('date_format', t.createTime, '%Y') as year " +
            "from #{#entityName} t where t.user.id = ?1 order by year desc ")
    List<String> findGroupYear(Long userId);


    /**
     * 查询该年份下发布过博客/问题的所有月份
     * 注意sql语句中不能使用%M 因为jpa似乎不能对%M得到的英文的月份进行排序
     *
     * @param year   需要查询的年份
     * @param userId 当前用户Id
     * @return 月份的List集合
     */
    @Query("select function('date_format', t.createTime, '%c') as month " +
            "from #{#entityName} t where function('date_format', t.createTime, '%Y') = ?1 " +
            "and t.user.id = ?2 order by month desc")
    List<String> findGroupMonthByYear(String year, Long userId);


    /**
     * 按月份查询问题/博客
     *
     * @param year 年份
     * @param month 月份
     * @param userId 当前用户Id
     * @return 博客/问题的List集合
     */
    @Query("select t from #{#entityName} t where function('date_format', t.createTime, '%Y') = ?1 and " +
            "function('date_format', t.createTime, '%c') = ?2 and t.user.id = ?3 order by t desc")
    List<T> findByYearAndMonth(String year, String month, Long userId);

    /**
     * 一个用户提出了多少个问题/博客
     *
     * @param userId 用户Id
     * @return 多少个
     */
    Long countAllByUserId(Long userId);

    /**
     * 找到前x个按y排序的博客/问题
     * 注意一定要有这个    @Query("select q from Question q") ！！不然找不到对应的bean
     *
     * @param pageable 内含分页顺序 其中的size属性与“Top”起限制作用
     * @return 返回前size个 按时间、影响力排序
     */
    @Query("select t from #{#entityName} t")
    List<T> findTop(Pageable pageable);
}
