package com.lrm.dao;

import com.lrm.po.Poster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PosterRepository extends JpaRepository<Poster, Long> {
}
