package com.lrm.web;

import com.lrm.exception.IllegalParameterException;
import com.lrm.po.InfoEntry;
import com.lrm.service.InfoEntryServiceImpl;
import com.lrm.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/infoEntry")
public class InfoEntryController {
    private final static Logger logger = LoggerFactory.getLogger(InfoEntryController.class);

    @Autowired
    private InfoEntryServiceImpl infoEntryServiceImpl;

    /**
     * 创建词条
     * @param infoEntry json格式的词条
     * @return 带有消息的Result
     */
    @PostMapping("/create")
    public Result create(@RequestBody @Valid InfoEntry infoEntry, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalParameterException(IllegalParameterException.getMessage(bindingResult));
        }
        //等待管理员检查
        infoEntryServiceImpl.saveInfoEntry(infoEntry);
        return new Result(null, "已提交，正在审核中");
    }

    /**
     * 更新词条
     * @param infoEntry json格式的词条
     * @return 带有消息的Result
     */
    @PostMapping("/{entryId}/update")
    public Result update(@RequestBody InfoEntry infoEntry, @PathVariable("entryId") Long entryId) {
        infoEntry.setId(entryId);
        infoEntryServiceImpl.update(infoEntry);
        return new Result(null, "已提交，正在审核中");
    }

    /**
     * 展示所有未审核的词条（管理员页面使用）
     * @return entries
     */
    @GetMapping("/unapprovedEntries")
    public Result unapprovedEntries() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("entries", infoEntryServiceImpl.getAllUnapproved());
        return new Result(hashMap, "需要审核的词条");
    }

    /**
     * 审核通过某个词条
     * @param entryId 词条id
     * @return msg
     */
    @PostMapping("/{entryId}/approve")
    public Result approveEntry(@PathVariable Long entryId) {
        infoEntryServiceImpl.approve(entryId);
        return new Result(null, "审核通过");
    }

    /**
     * 展示所有审核通过的词条
     * @return 需要展示的词条
     */
    @GetMapping("/show")
    public Result showEntries() {
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("entries", infoEntryServiceImpl.getApprovedByTime());
        return new Result(hashMap, "需要展示的词条");
    }

}
