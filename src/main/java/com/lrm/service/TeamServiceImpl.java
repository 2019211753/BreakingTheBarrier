package com.lrm.service;

import com.lrm.dao.TeamRepository;
import com.lrm.po.Team;
import com.lrm.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public void addMember() {

    }

    @Override
    public void delMember() {

    }

    @Override
    public Team createTeam(String name) {
        Team team = new Team();
        team.setNickname(name);
        team.setLeader(UserHolder.getLocalUser());
        teamRepository.save(team);
        return team;
    }

    @Override
    public void changeTeamName() {

    }

    @Override
    public void transferLeader() {

    }

    @Override
    public void delTeam() {

    }
}
