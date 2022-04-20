package com.lrm.web;

import com.lrm.po.Team;
import com.lrm.service.TeamService;
import com.lrm.vo.Result;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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
    public Result create(@RequestParam("teamName") String name, @RequestParam("teamCapacity") int capacity) {
        Team newTeam = teamService.createTeam(name, capacity);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("team", newTeam);
        return new Result(hashMap, "队伍创建成功");
    }


    @PostMapping("/addMember")
    public Result addMember(@RequestParam("teamId") Long teamId, @RequestParam("userId") Long userId) {
        Team team = teamService.addMember(teamId, userId);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("team", team);
        return new Result(hashMap, "成功添加队员");
    }

    @PostMapping("/delMember")
    public Result delMember(@RequestParam("teamId") Long teamId, @RequestParam("userId") Long userId) {
        Team team = teamService.delMember(teamId, userId);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("team", team);
        return new Result(hashMap, "成功删除队员");
    }

    @PostMapping("/joinTeam")
    public Result join() {
        return null;
    }

    @PostMapping("/leaveTeam")
    public Result leave() {
        return null;
    }

    @PostMapping("/addPoster")
    public Result addPoster() {
        return null;
    }

}
