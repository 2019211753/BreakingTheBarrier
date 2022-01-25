package com.lrm.service;

import com.lrm.po.Team;
import org.springframework.stereotype.Service;

@Service
public interface TeamService {
    public void addMember();

    public void delMember();

    public Team createTeam(String name);

    public void changeTeamName();

    public void transferLeader();

    public void delTeam();

}
