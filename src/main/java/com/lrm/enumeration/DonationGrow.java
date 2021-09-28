package com.lrm.enumeration;

/**
 * 类<code>Doc</code>用于：TODO
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-09-05
 */
public enum DonationGrow {
    /**
     * POST_TEMPLATE 发布博客/问题时增加的贡献值
     * POST_EFFECTIVE_COMMENT 发布有效评论时
     * APPROVED_TEMPLATE 博客/问题被点赞时
     * TEMPLATE_COLLECTED 博客/问题被收藏时
     * FOLLOWED 被关注时
     */
    POST_TEMPLATE(2),
    POST_EFFECTIVE_COMMENT(4),
    TEMPLATE_APPROVED(2),
    TEMPLATE_COLLECTED(2),
    COMMENT_APPROVED(4),
    COMMENT_SELECTED(8),
    FOLLOWED(2);

    Integer grow;

    DonationGrow(Integer grow) {
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
