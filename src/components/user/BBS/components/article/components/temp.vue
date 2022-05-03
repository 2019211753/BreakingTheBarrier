<template>
  <div v-for="(item,index) in contentList">
    <!--用户-->
    <div class="item articleDis">
      <img :src="item.avatar" alt="" class="ui avatar image"/>
      <div class="content">
        <a class="header">
          {{ item.nickname }}
        </a>
      </div>
    </div>
    <!--标题-->
    <h3 class="btb-h3">
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
    <!--简述-->
    <p class="btb-h4 articleDis">{{ item.description }}</p>
    <!--时间-->
    <div class="item articleDis">
      <i class="clock icon"></i>
      <span class="" style="margin-left: 2px">{{ item.createTime }}</span>
    </div>
    <!--点赞数-->
    <div class="item">
      <i class="thumbs up icon"></i>
      <span style="margin-left: 2px">{{ item.likesNum }}</span>
    </div>
    <!--评论量-->
    <div class="item">
      <i class="comment icon"></i>
      <span style="margin-left: 2px">{{ item.commentsNum }}</span>
    </div>
    <!--浏览量-->
    <div class="item">
      <i class="eye icon"></i>
      <span style="margin-left: 2px">{{ item.view }}</span>
    </div>
    4<!--标签-->
    <a style="margin: 5px 0" class="ui teal basic label" v-for="tags in item.tags">
      {{tags.name }}
    </a>
    <!--是否解决-->
    <a style="margin: 5px 0" class="ui teal basic label" v-if="item.solved == true">已解决</a>
    <a class="ui red basic label" v-else>未解决</a>
    <!--骨架屏-->
    <el-skeleton :loading="loading" animated :count="7">
      <template slot="template">
        <div class="ui segment">
          <div class="ui middle aligned grid">
            <div class="ten wide column">
              <el-skeleton-item variant="text"/>
              <el-skeleton-item
                variant="text"
              />
              <el-skeleton-item variant="text"/>
              <el-skeleton-item
                variant="text"
              />
              <el-skeleton-item variant="text"/>
              <el-skeleton-item
                variant="text"
              />
              <el-skeleton-item variant="text"/>
              <el-skeleton-item
                variant="text"
              />
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
    8<!--分页-->
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
