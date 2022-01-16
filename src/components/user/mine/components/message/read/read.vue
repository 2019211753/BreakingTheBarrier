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
          <img :src=" item.avatar" alt="" />
        </div>
        <div class="content">
          <div class="summary">
            <a class="user"> {{ item.nickname }} </a> 评论了你：
            <a href=""
              ><router-link
                v-if="item.blogId"
                :to="{
                  path: '/helloWorld/BBS/blogContent',
                  query: { blogId: item.blogId0 },
                }"
              >
                {{ item.content }}</router-link
              ><router-link
                v-else
                :to="{
                  path: '/helloWorld/BBS/articleContent',
                  query: { articleId: item.questionId0 },
                }"
              >
                {{ item.content }}</router-link
              ></a
            >
            <div class="date">{{ item.createTime }}</div>
          </div>
        </div>
      </div>
      <div class="event" v-if="lookedLikes" v-for="item in lookedLikes">
        <div class="label">
          <img :src="  item.avatar" alt="" />
        </div>
        <div class="content">
          <div class="summary">
            <a class="user"> {{ item.nickname }} </a> 点赞了你的
            <a href=""
              ><router-link
                v-if="item.blogId"
                :to="{
                  path: '/helloWorld/BBS/blogContent',
                  query: { blogId: item.blogId0 },
                }"
              >
                内容</router-link
              ><router-link
                v-else
                :to="{
                  path: '/helloWorld/BBS/articleContent',
                  query: { articleId: item.questionId0 },
                }"
              >
                内容</router-link
              ></a
            >
            <div class="date">{{ item.createTime }}</div>
          </div>
        </div>
      </div>
    </div>
    <el-skeleton :loading="loading" animated :count="5">
      <template slot="template"
        ><div class="ui large feed">
          <div class="event">
            <div class="label">
              <el-skeleton-item variant="circle image" />
            </div>
            <div class="content">
              <div class="summary">
                <el-skeleton-item variant="text" />
              </div>
            </div>
          </div>
        </div>
      </template>
    </el-skeleton>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "read",
  data() {
    return { loading: true, lookedComments: [], lookedLikes: [] };
  },
  created() {
    var that = this;
    axios
      .get("/customer/messages/")
      .then(function (response) {
        that.loading = false;
        that.lookedComments = response.data.data.lookedComments;
        that.lookedLikes = response.data.data.lookedLikes;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
};
</script>

<style scoped>
</style>
