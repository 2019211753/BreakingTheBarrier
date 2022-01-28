package com.lrm.web;

import com.lrm.po.Team;
import com.lrm.service.TeamService;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @PostMapping("/create")
    public Result create(@RequestParam("teamName") String name) {
        Team newTeam = teamService.createTeam(name);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("team", newTeam);
        return new Result(hashMap, "队伍创建成功");
    }
}
