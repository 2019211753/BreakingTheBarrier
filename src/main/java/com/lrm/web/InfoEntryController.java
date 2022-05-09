package com.lrm.web;

import com.lrm.dto.CreateInfoEntryDTO;
import com.lrm.dto.UpdateInfoEntryDTO;
import com.lrm.exception.FailedOperationException;
import com.lrm.exception.IllegalParameterException;
import com.lrm.po.InfoEntry;
import com.lrm.service.InfoEntryServiceImpl;
import com.lrm.util.FileUtils;
import com.lrm.util.LockHolder;
import com.lrm.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/infoEntry")
public class InfoEntryController {
    private final static Logger logger = LoggerFactory.getLogger(InfoEntryController.class);
    private final static ReentrantLock lock = new ReentrantLock(true);
    private final static Condition condition = lock.newCondition();
    /** 用来判断不同线程是否修改的是相同的对象，如果相同则对线程上锁执行， */
    private volatile static long volatileId;
    @Autowired
    private InfoEntryServiceImpl infoEntryServiceImpl;

    /**
     * 创建词条
     * @return 带有消息的Result
     */
    @PostMapping("/create")
    public Result create(@RequestBody CreateInfoEntryDTO createInfoEntryDTO) {
//        if (bindingResult.hasErrors()) {
//            throw new IllegalParameterException(IllegalParameterException.getMessage(bindingResult));
//        }

        String realTagNames = createInfoEntryDTO.getEntryTags().substring(1, createInfoEntryDTO.getEntryTags().length() - 1);
        String[] tagNames = realTagNames.split(",");
        InfoEntry infoEntry = new InfoEntry();
        infoEntry.setCurrentContent(createInfoEntryDTO.getCurrentContent());
        infoEntry.setAlias(createInfoEntryDTO.getAlias());
        infoEntry.setNewContent(createInfoEntryDTO.getNewContent());
        infoEntry.setTitle(createInfoEntryDTO.getTitle());
        infoEntry.setDiscription(createInfoEntryDTO.getDiscription());
        for (int i = 0; i < tagNames.length; i++) {
            tagNames[i] = tagNames[i].substring(1, tagNames[i].length() - 1);
        }
        //等待管理员检查
        try {
            infoEntryServiceImpl.saveInfoEntry(infoEntry, tagNames);
        } catch (FailedOperationException e) {
            logger.error("title duplicated", e);
            return new Result(null, "同名词条已经存在");
        }
        return new Result(null, "已提交，正在审核中");
    }

    /**
     * 更新词条
     * @param infoEntry json格式的词条
     * @return 带有消息的Result
     */
    @PostMapping("/update")
    public Result update(@RequestBody UpdateInfoEntryDTO updateInfoEntryDTO) {
        String realTagNames = updateInfoEntryDTO.getEntryTags().substring(1, updateInfoEntryDTO.getEntryTags().length() - 1);
        String[] tagNames = realTagNames.split(",");
        for (int i = 0; i < tagNames.length; i++) {
            tagNames[i] = tagNames[i].substring(1, tagNames[i].length() - 1);
        }
        infoEntryServiceImpl.update(updateInfoEntryDTO, tagNames);
        return new Result(null, "已提交，正在审核中");
    }

    /**
     * 展示所有未审核的词条（管理员页面使用）
     * @return entries
     */
    @GetMapping("/unapprovedEntries")
    public Result unapprovedEntries(@RequestParam("pageIndex") int pageIndex) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("entries", infoEntryServiceImpl.getUnapproved(pageIndex));
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

    @PostMapping("/{entryId}/disapprove")
    public Result disapproveEntry(@PathVariable Long entryId) {
        infoEntryServiceImpl.disapprove(entryId);
        return new Result(null, "审核未通过");
    }

    /**
     * 展示所有审核通过的词条
     * @return 需要展示的词条
     */
    @GetMapping("/show")
    public Result showEntries(@RequestParam("pageIndex") int pageIndex) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("entries", infoEntryServiceImpl.getApprovedByTime(pageIndex));
        return new Result(hashMap, "需要展示的词条");
    }

    @GetMapping("/search")
    public Result search(@RequestParam("pageIndex") int pageIndex, @RequestParam("query") String query) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("entries", infoEntryServiceImpl.searchEntry(pageIndex, query));
        return new Result(hashMap, "搜索到的词条");
    }

    @PostMapping("/delete/{entryId}")
    public Result delete(@PathVariable Long entryId) {
        infoEntryServiceImpl.deleteEntry(entryId);
        return new Result(null, "成功删除");
    }

    @GetMapping("/search/{id}")
    public Result searchById(@PathVariable("id") Long id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("entries", infoEntryServiceImpl.searchEntryById(id));
        return new Result(hashMap, "搜索到的词条");
    }
}
