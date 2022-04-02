package com.lrm.dao;

import com.lrm.po.EntryTag;
import com.lrm.po.FileTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface EntryTagRepository extends JpaRepository<EntryTag, Long> {
    /**
     * 标签名查招
     * @param name
     * @return
     */
    EntryTag findByName(String name);

    /**
     * 查找标签 按pageable内定义的排序顺序和数量返回
     * @param pageable
     * @return
     */
    @Query("select et from EntryTag et")
    List<EntryTag> findTop(Pageable pageable);
}
