package com.lrm.service;

import com.lrm.dao.TagRepository;
import com.lrm.exception.NotFoundException;
import com.lrm.po.Tag;
import com.lrm.util.DataStructureUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository tagRepository;

    /**
     * 临时存放标签
     */
    public Set<Tag> tagSet = new HashSet<>();

    //简单的增删改查

    @Override
    @Transactional
    public Tag saveTag(Tag tag) {
        Long parentTagId = tag.getParentTagId0();
        if (parentTagId != -1) {
            Optional<Tag> parentTag = tagRepository.findById(parentTagId);
            if (!parentTag.isPresent()) {
                throw new NotFoundException("该父标签不存在");
            } else {
                tag.setParentTag(parentTag.get());
                tag.setParentTagId0(parentTagId);
            }

        } else {
            //对象new了(初始化id为-1了) 但没有持久化会报错 所以设成null
            tag.setParentTag(null);
            tag.setParentTagId0(-1L);
        }
        return tagRepository.save(tag);
    }

    @Override
    @Transactional
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Tag updateTag(Tag tag) {
        Optional<Tag> opTag = tagRepository.findById(tag.getId());
        Tag t = opTag.get();
        BeanUtils.copyProperties(tag, t);
        return tagRepository.save(t);
    }


    @Override
    public Tag getTag(Long id) {
        Optional<Tag> opTag = tagRepository.findById(id);
        return opTag.orElse(null);
    }

    /**
     * 通过标签名找标签 查询是否重复
     */
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    /**
     * 首级标签展示
     */
    @Override
    public List<Tag> listTagTop() {
        List<Tag> tagList = tagRepository.findByParentTagNull();
        for (Tag tag : tagList) {
            insertChildTags(tag);
        }
        return tagList;
    }

    /**
     * 将String对象转为Tag集合
     *
     * @param ids 前端以,分割的tagId
     * @return 标签集合
     */
    @Override
    public List<Tag> listTags(String ids) { //1,2,3
        List<Tag> tags = new ArrayList<>();
        List<Long> tagIds = DataStructureUtils.convertToList(ids);
        //这里没有对应标签就不抛异常了 找有标签的就行
        for (Long tagId : tagIds) {
            tags.add(getTag(tagId));
        }
        return tags;
    }

    /**
     * 由某标签列出其下所有标签
     */
    @Override
    public Set<Tag> listTags(Tag tag)
    {
        tagSet.add(tag);
        List<Tag> tags = tag.getSonTags();
        if (tags.size() == 0)
        {
            return tagSet;
        }
        for (Tag tag1 : tags) {
            listTags(tag1);

        }
        return tagSet;
    }

    /**
     * 得到去重&递增&有对应标签的tagIds
     *  如tagIds = 1,3,2 转换为1,2,3
     * 去重后检查tag是否存在
     *
     * @param tagIds 需要转换的字符串
     *               为什么要这样转换？方便在高级搜索中用predicate关联查询。
     * @return 转换完成的tagIds
     */
    @Override
    public String listTagIdsFromSmallToBig(String tagIds) {
        String[] ids = tagIds.split(",");
        ids = DataStructureUtils.removeDuplicateElement(ids);
        //ids的非null部分长度
        int length = 0;
        for (String id : ids) {
            if (id != null) {
                length++;
            }
        }
        String[] newIds = new String[length];
        System.arraycopy(ids, 0, newIds, 0, length);
        //得到排序、去重后的新ids
        Arrays.sort(newIds, (a, b) -> {
            Long c = Long.parseLong(a);
            Long d = Long.parseLong(b);
            return c.compareTo(d);
        });

        StringBuilder tagIdsBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (getTag(Long.parseLong(newIds[i])) != null) {
                tagIdsBuilder.append(newIds[i]);
                tagIdsBuilder.append(",");
            }
        }

        if (tagIdsBuilder.length() == 0) {
            throw new NotFoundException("未查询到你选择的标签");
        }

        tagIdsBuilder.delete(tagIdsBuilder.length() - 1, tagIdsBuilder.length());

        return tagIdsBuilder.toString();
    }

    public void insertChildTags(Tag tag) {
        if (tag.getSonTags().size() != 0) {
            tag.setChildTags(tag.getSonTags());
            for (Tag sonTag : tag.getChildTags()) {
                insertChildTags(sonTag);
            }
        }
    }
}
