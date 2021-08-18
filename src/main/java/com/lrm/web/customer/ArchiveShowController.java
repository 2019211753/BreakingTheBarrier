package com.lrm.web.customer;

import com.lrm.po.Template;
import com.lrm.service.BlogServiceImpl;
import com.lrm.service.QuestionServiceImpl;
import com.lrm.service.TemplateServiceImpl;
import com.lrm.util.TokenInfo;
import com.lrm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 归档
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@RestController
@RequestMapping("/customer")
public class ArchiveShowController {
    @Autowired
    private QuestionServiceImpl questionServiceImpl;

    @Autowired
    private BlogServiceImpl blogServiceImpl;

    /**
     * 按年份归档 时间逆序
     *
     * @param request 获取当前用户id
     * @return 已经分类的问题
     */
    @GetMapping("/archivesQuestion")
    public Result archivesQuestion(HttpServletRequest request) {
        return archives(request, questionServiceImpl);
    }

    /**
     * 按年份归档 时间逆序
     *
     * @param request 获取当前用户id
     * @return 已经分类的博客
     */
    @GetMapping("/archivesBlog")
    public Result archivesBlog(HttpServletRequest request) {
        return archives(request, blogServiceImpl);
    }

    <T extends Template> Result archives(HttpServletRequest request, TemplateServiceImpl<T> templateServiceImpl) {
        Map<String, Object> hashMap = new HashMap<>(2);

        Long userId = TokenInfo.getCustomUserId(request);

        hashMap.put("archiveMap", templateServiceImpl.archivesByUserId(userId));
        hashMap.put("count", templateServiceImpl.countAllByUserId(userId));

        return new Result(hashMap, "");
    }
}
