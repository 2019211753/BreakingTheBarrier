package com.lrm.service;

import com.lrm.dao.TeamRepository;
import com.lrm.dao.UserRepository;
import com.lrm.exception.FailedOperationException;
import com.lrm.exception.NotFoundException;
import com.lrm.po.Team;
import com.lrm.po.User;
import com.lrm.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Team addMember(Long teamId, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent())
            throw new NotFoundException("无此用户");
        Optional<Team> team = teamRepository.findById(teamId);
        if (!team.isPresent())
            throw new NotFoundException("无此队伍");
        if (team.get().getMembers().size() >= team.get().getCapacity())
            throw new FailedOperationException("队伍已满");
        team.get().getMembers().add(user.get());
        return team.get();
    }

    @Override
    public Team delMember(Long teamId, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent())
            throw new NotFoundException("无此用户");
        Optional<Team> team = teamRepository.findById(teamId);
        if (!team.isPresent())
            throw new NotFoundException("无此队伍");
        team.get().getMembers().remove(user.get());
        return team.get();
    }

    @Override
    public Team createTeam(String name, int capacity) {
        Team team = new Team();
        team.setNickname(name);
        team.setLeader(UserHolder.getLocalUser());
        team.setCapacity(capacity);
        teamRepository.save(team);
        return team;
    }

    @Override
    public Team changeTeamName(Long teamId, String name) {
        User leader = UserHolder.getLocalUser();
        Optional<Team> team = teamRepository.findById(teamId);

        if (!team.isPresent())
            throw new NotFoundException("无此队伍");

        if (leader == null || !team.get().getLeader().equals(leader))
            throw new NotFoundException("组长不存在或用户不是组长");

        team.get().setNickname(name);
        return team.get();
    }

    @Override
    public Team transferLeader(Long teamId, Long userId) {
        return null;
    }

    @Override
    public boolean delTeam(Long teamId) {
        return true;
    }
}
