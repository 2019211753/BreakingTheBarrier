package com.lrm.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

/**
 * 数据结构间转换
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public class ProcessData {

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
     * @param set 原set集合 应该是LinkListSet集合
     * @param <T> 集合泛型
     * @return 按原set集合add的顺序得到的List集合
     */
    public static <T> List<T> setConvertToList(Set<T> set) {
        List<T> list = new LinkedList<>();
        for (T x : set) {
            list.add(x);
        }
        return list;
    }
}
