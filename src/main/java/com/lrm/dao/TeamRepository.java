package com.lrm.dao;

import com.lrm.po.Team;
import com.lrm.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAllByLeader(User leader);

    List<Team> findAllByNickname(String name);

}
