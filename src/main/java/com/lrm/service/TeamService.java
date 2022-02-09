package com.lrm.service;

import com.lrm.po.Team;
import org.springframework.stereotype.Service;

@Service
public interface TeamService {
    public Team addMember(Long teamId, Long userId);

    public Team delMember(Long teamId, Long userId);

    public Team createTeam(String name, int capacity);

    public Team changeTeamName(Long teamId, String name);

    public Team transferLeader(Long teamId, Long userId);

    public boolean delTeam(Long teamId);

}
