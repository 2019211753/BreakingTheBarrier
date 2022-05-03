<template>
  <div>
    <!--暂无内容-->
    <div class="ui segment" v-if="loading == false && !contentList.length">
      <el-empty image-size="200" description="暂无内容"></el-empty>
    </div>
    <!--标题-->
    <h3 class="ui header">
      <router-link
        :to="{
                  path: '/BreakingTheBarrier/BBS/articleContent',
                  query: {
                    articleId: item.id,
                    postUserId: item.postUserId,
                  },
                }"
      >{{ item.title }}
      </router-link>
    </h3>
    <!--简介-->
    <p class="m-text">{{ item.description }}</p>
    <!--用户-->
    <div class="item">
      <img :src="item.avatar" alt="" class="ui avatar image" />
      <div class="content">
        <a class="header">{{ item.nickname }}</a>
      </div>
    </div>
    <!--点赞量、评论量、时间-->
    <div class="ui mini horizontal link list">
      <div class="item">
        <i class="calendar icon"></i>
        <span style="margin-left: 2px">{{ item.createTime }}</span>
      </div>
      <div class="item">
        <i class="thumbs up icon"></i>
        <span style="margin-left: 2px">{{ item.likesNum }}</span>
      </div>
      <div class="item">
        <i class="comment icon"></i>
        <span style="margin-left: 2px">{{ item.commentsNum }}</span>
      </div>
      <div class="item">
        <i class="eye icon"></i>
        <span style="margin-left: 2px">{{ item.view }}</span>
      </div>
    </div>
    <!--标签-->
    <a class="ui teal basic label" v-for="tags in item.tags">{{
        tags.name
      }}</a>
    <!--是否解决-->
    <a class="ui teal basic label" v-if="item.solved == true">已解决</a>
    <a class="ui red basic label" v-else>未解决</a>
    <!--骨架屏-->
    <el-skeleton :loading="loading" animated :count="7">
      <template slot="template">
        <div class="ui segment">
          <div class="ui middle aligned grid">
            <div class="ten wide column">
              <el-skeleton-item variant="text" />
              <el-skeleton-item variant="text" />
              <el-skeleton-item variant="text" />
              <el-skeleton-item variant="text" />
              <el-skeleton-item variant="text" />
              <el-skeleton-item variant="text" />
              <el-skeleton-item variant="text" />
              <el-skeleton-item variant="text" />
            </div>
            <div class="six wide column">
              <el-skeleton-item
                variant="image"
                style="height: 120px; border-radius: 5px"
              />
            </div>
          </div>
        </div>
      </template>
    </el-skeleton>
    <!--分页-->
    <el-pagination
      class="el-pagination"
      layout="prev, pager, next"
      @current-change="handleCurrentChange"
      :page-count="pageSize"
      :hide-on-single-page="true"
    >
    </el-pagination>
  </div>
</template>

<script>
export default {
  name: "temp"
}
</script>

<style scoped>

</style>
