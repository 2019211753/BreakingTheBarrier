package com.lrm.web;

import com.lrm.dao.PosterRepository;
import com.lrm.exception.FailedOperationException;
import com.lrm.exception.IllegalParameterException;
import com.lrm.po.InfoEntry;
import com.lrm.po.Poster;
import com.lrm.po.Team;
import com.lrm.service.PosterServiceImpl;
import com.lrm.service.TeamService;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @Autowired
    private PosterRepository posterRepository;

    @Autowired
    private PosterServiceImpl posterService;

//    @PostMapping("/create")
//    public Result create(@RequestParam("teamName") String name, @RequestParam("teamCapacity") int capacity) {
//        Team newTeam = teamService.createTeam(name, capacity);
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("team", newTeam);
//        return new Result(hashMap, "队伍创建成功");
//    }


//    @PostMapping("/addMember")
//    public Result addMember(@RequestParam("teamId") Long teamId, @RequestParam("userId") Long userId) {
//        Team team = teamService.addMember(teamId, userId);
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("team", team);
//        return new Result(hashMap, "成功添加队员");
//    }
//
//    @PostMapping("/delMember")
//    public Result delMember(@RequestParam("teamId") Long teamId, @RequestParam("userId") Long userId) {
//        Team team = teamService.delMember(teamId, userId);
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("team", team);
//        return new Result(hashMap, "成功删除队员");
//    }

    /**
     * 创建词条
     * @param pos json格式的词条
     * @return 带有消息的Result
     */
    @PostMapping("/create")
    public Result create(@RequestBody Poster pos) {
        posterService.savePoster(pos);
        return new Result(null, "已提交");
    }

    /**
     * 更新词条
     * @return 带有消息的Result
     */
    @PostMapping("/{entryId}/update")
    public Result update(@RequestBody Poster poster, @PathVariable("posterId") Long posterId) {
        poster.setId(posterId);
        posterService.savePoster(poster);
        return new Result(null, "已提交，正在审核中");
    }

    @GetMapping("/search/{id}")
    public Result searchById(@PathVariable("id") Long id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("entries", posterService.getPoster(id));
        return new Result(hashMap, "搜索到的帖子");
    }

    @GetMapping("/show")
    public Result showEntries(@RequestParam("pageIndex") int pageIndex) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("entries", posterRepository.findAll());
        return new Result(hashMap, "需要展示的帖子");
    }
}
