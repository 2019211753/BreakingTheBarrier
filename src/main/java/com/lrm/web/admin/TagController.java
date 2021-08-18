package com.lrm.web.admin;

import com.lrm.exception.FailedOperationException;
import com.lrm.exception.IllegalParameterException;
import com.lrm.exception.NotFoundException;
import com.lrm.po.Tag;
import com.lrm.service.TagServiceImpl;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员控制标签
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@RestController
@RequestMapping("/admin/tags")
public class TagController {
    @Autowired
    private TagServiceImpl tagServiceImpl;

    /**
     * 新增或修改标签
     *
     * @param tag    前端封装好的Tag对象
     * @param result 标签名校验处理
     * @return 返回报错信息; 已保存的Tag对象
     */
    @PostMapping("/input")
    public Result post(@RequestBody @Valid Tag tag, BindingResult result) {
        Map<String, Object> hashMap = new HashMap<>(1);

        if (result.hasErrors()) {
            throw new IllegalParameterException(IllegalParameterException.getMessage(result));
        }

        //检查是否存在同名标签 注意不区分大小写
        Tag tag0 = tagServiceImpl.getTagByName(tag.getName());
        if (tag0 != null) {
            hashMap.put("tag", tag);
            throw new FailedOperationException("不能添加同名的标签");
        }

        //检查是新增操作吗
        if (tag.getId() == null) {
            Tag t = tagServiceImpl.saveTag(tag);
            if (t == null) {
                throw new FailedOperationException("新增失败");
            } else {
                hashMap.put("tag", t);
                return new Result(hashMap, "新增成功");
            }
        }

        //如果是修改
        Tag t = tagServiceImpl.updateTag(tag);
        if (t == null) {
            throw new FailedOperationException("修改失败");
        } else {
            hashMap.put("tag", t);
            return new Result(hashMap, "修改成功");
        }
    }

    /**
     * 删除标签
     *
     * @param tagId 标签id
     * @return 成功\失败信息
     */
    @GetMapping("/{tagId}/delete")
    public Result delete(@PathVariable Long tagId) {
        Tag tag = tagServiceImpl.getTag(tagId);
        if (tag == null) {
            throw new NotFoundException("未查询到该标签");
        }

        tagServiceImpl.deleteTag(tagId);
        tag = tagServiceImpl.getTag(tagId);
        if (tag != null) {
            throw new FailedOperationException("删除失败");
        } else {
            return new Result(null, "删除成功");
        }
    }


}
