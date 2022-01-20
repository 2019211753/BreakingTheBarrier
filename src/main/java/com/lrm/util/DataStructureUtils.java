package com.lrm.util;

import com.lrm.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数据结构间转换
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public class DataStructureUtils {

    /**
     * @param list 某集合
     * @return 去重后的集合
     */
    public static <E> List<E> removeDuplicateElement(List<E> list) {
        List<E> listNew = new ArrayList<>();
        for (E e : list) {
            if (!listNew.contains(e)) {
                listNew.add(e);
            }
        }
        return listNew;
    }

    /**
     * @param array 某数组
     * @return 去重后的数组 如果数组中有相同的元素，则含null
     */
    public static <E> E[] removeDuplicateElement(E[] array) {
        List<E> list = Arrays.asList(array);
        //去重后的集合
        List<E> listNew = removeDuplicateElement(list);
        //转换为数组 这个arrayNew的前listNew.size()个元素跟listNew一样
        //第listNew.size()+1为null，剩下的没变，所以我还要在这里手动变成null
        E[] arrayNew = listNew.toArray(array);
        if (arrayNew.length > listNew.size()) {
            for (int i = listNew.size(); i < arrayNew.length; i++) {
                arrayNew[i] = null;
            }
        }
        return arrayNew;
    }

    /**
     * 参考使用Page.map
     * 将List转换为Page
     *
     * @param <T>      list泛型
     * @param list     需要转换的List集合
     * @param pageable 分页格式
     * @return 转换结果
     */
    public static <T> Page<T> listConvertToPage(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }

    /**
     * @param ids 将tagIds分割
     * @return 分割结果List集合
     */
    public static List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idArray = ids.split(",");
            for (String s : idArray) {
                list.add(Long.parseLong(s));
            }
        }
        return list;
    }

    /**
     * 前端Tag对象的格式是以,分割的 tagIds作为一个媒介
     *
     * @param tags 与question关联的tag集合
     * @return , 分割的tagId
     */
    public static String tagsToIds(List<Tag> tags) {
        StringBuilder ids = new StringBuilder();
        boolean flag = false;
        for (Tag tag : tags) {
            if (flag) {
                ids.append(",");
            } else {
                flag = true;
            }
            ids.append(tag.getId());
        }
        return ids.toString();

    }
}
