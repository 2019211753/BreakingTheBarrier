<template>
  <div class="ui segment">
    <div class="ui feed">
      <el-empty
        :image-size="100"
        v-if="!lookedLikes.length && !lookedComments.length && loading == false"
        description="暂无消息"
      ></el-empty>
      <div class="event" v-if="lookedComments" v-for="item in lookedComments">
        <div class="label">
          <img :src="item.avatar" alt=""/>
        </div>
        <div class="content">
          <div class="summary">
            <a class="user"> {{ item.nickname }} </a> 在
            <a class="user"> {{ getInnerText(item.parentContent) }} </a> 中评论了你：
            <a href=""
            >
              <router-link
                v-if="item.parentType == '博客' || item.parentType == '博客的评论'"
                :to="{
                  path: '/BreakingTheBarrier/BBS/blogContent',
                  query: { blogId: item.parentId,postUserId: item.parentUserId, },
                }"
              >
                {{ getInnerText(item.content) }}
              </router-link
              >
              <router-link
                v-else
                :to="{
                  path: '/BreakingTheBarrier/BBS/articleContent',
                  query: { articleId: item.parentId,postUserId: item.parentUserId, },
                }"
              >
                {{ getInnerText(item.content) }}
              </router-link
              >
            </a
            >
            <div class="date">{{ item.createTime }}</div>
          </div>
        </div>
      </div>
      <div class="event" v-if="lookedLikes" v-for="item in lookedLikes">
        <div class="label">
          <img :src="item.avatar" alt=""/>
        </div>
        <div class="content">
          <div class="summary">
            <a class="user">
              {{ item.nickname }} </a> 点赞了你的{{
              item.parentType
            }}：
            <a href=""
            >
              <router-link
                v-if="item.parentType == '博客' || item.parentType == '博客的评论'"
                :to="{
                  path: '/BreakingTheBarrier/BBS/blogContent',
                  query: { blogId: item.parentId,postUserId: item.parentUserId, },
                }"
              >
                {{ getInnerText(item.parentContent) }}
              </router-link
              >
              <router-link
                v-else
                :to="{
                  path: '/BreakingTheBarrier/BBS/articleContent',
                  query: { articleId: item.parentId,postUserId: item.parentUserId, },
                }"
              >
                {{ getInnerText(item.parentContent) }}
              </router-link
              >
            </a
            >
            <div class="date">{{ item.createTime }}</div>
          </div>
        </div>
      </div>
    </div>
    <el-skeleton :loading="loading" animated :count="5">
      <template slot="template"
      >
        <div class="ui large feed">
          <div class="event">
            <div class="label">
              <el-skeleton-item variant="circle image"/>
            </div>
            <div class="content">
              <div class="summary">
                <el-skeleton-item variant="text"/>
              </div>
            </div>
          </div>
        </div>
      </template>
    </el-skeleton>
  </div>
</template>

<script>

export default {
  name: "read",
  data() {
    return {loading: true, lookedComments: [], lookedLikes: []};
  },
  created() {
    var that = this;
    that.$api.personalMessage.getAllMessage()
      .then(function (response) {
        that.loading = false;
        that.lookedComments = response.data.data.lookedComments;
        that.lookedLikes = response.data.data.lookedLikes;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    getInnerText(content) {
      var oDiv = document.createElement("div");
      oDiv.innerHTML = content;
      console.log(oDiv.innerHTML);
      return oDiv.innerText;
    },
  },
};
</script>

<style scoped>
</style>
