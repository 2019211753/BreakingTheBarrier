package com.lrm.enumeration;

/**
 * 类<code>Doc</code>用于：TODO
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-09-05
 */
public enum ImpactGrow {
    /**
     * CLICKED 博客/问题被点击
     * COMMENTED 博客/问题被评论
     * MAX_APPROVED 博客/问题的最高赞数的评论对Impact的加权
     * APPROVED 博客/问题被点赞
     * COLLECTED 博客/问题被收藏
     */
    CLICKED(2),
    COMMENTED(2),
    MAX_APPROVED(12),
    APPROVED(2),
    COLLECTED(2);

    Integer grow;

    ImpactGrow(Integer grow) {
        this.grow = grow;
    }

    public Integer getGrow() {
        return grow;
    }

    @Override
    public String toString() {
        return grow.toString();
    }
}
