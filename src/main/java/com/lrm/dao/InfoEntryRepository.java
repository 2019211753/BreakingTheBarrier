package com.lrm.dao;

import com.lrm.po.File;
import com.lrm.po.InfoEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InfoEntryRepository extends JpaRepository<InfoEntry, Long> {
    @Query("select ie from InfoEntry ie where ie.isApproved = false ")
    Page<InfoEntry> findUnapproved(Pageable pageable);

    @Query("select ie from InfoEntry ie where ie.title like %?1%")
    Page<InfoEntry> findByTitle(String title, Pageable pageable);

    Page<InfoEntry> findAllByisApprovedOrderByLastApprovedTimeDesc(Boolean isApproved, Pageable pageable);
}
