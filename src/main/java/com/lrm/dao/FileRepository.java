package com.lrm.dao;

import com.lrm.po.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface FileRepository extends JpaRepository<File, Long>, JpaSpecificationExecutor<File> {
    @Query("select f from File f where f.originName like %?1%")//记得加%占位符
    Page<File> findByQuery(String query, Pageable pageable);
    //调用者写法: repositroy.findByQuery("自动机", new PageRequest(1, 10)); page, size, sort

    File findByName(String name);
}
