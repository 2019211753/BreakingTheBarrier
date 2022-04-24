package com.lrm.dao;

import com.lrm.po.InfoEntry;
import com.lrm.po.Poster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PosterRepository extends JpaRepository<Poster, Long> {
    @Query("select ie from Poster ie where ie.title like %?1%")
    Page<Poster> findByTitle(String title, Pageable pageable);

    Page<Poster> findTop5ByisApprovedOrderByLastApprovedTimeDesc(Boolean isApproved, Pageable pageable);
}
