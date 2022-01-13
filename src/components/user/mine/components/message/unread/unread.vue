<template>
  <div class="ui segment">
    <div class="ui feed">
      <el-empty
        :image-size="100"
        v-if="
          !unLookedLikes.length && !unLookedComments.length && loading == false
        "
        description="暂无消息"
      ></el-empty>
      <div
        class="event"
        v-if="unLookedComments"
        v-for="item in unLookedComments"
      >
        <div class="label">
          <img :src="'data:image/jpg;base64,' + item.avatar" alt="" />
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
                {{item.content}}</router-link
              ><router-link
                v-else
                :to="{
                  path: '/helloWorld/BBS/articleContent',
                  query: { articleId: item.questionId0 },
                }"
              >
                 {{item.content}}</router-link
              ></a
            >
            <div class="date">{{ item.createTime }}</div>
          </div>
        </div>
      </div>
      <div class="event" v-if="unLookedLikes" v-for="item in unLookedLikes">
        <div class="label">
          <img :src="'data:image/jpg;base64,' + item.avatar" alt="" />
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
  name: "unread",
  data() {
    return { loading: true, unLookedComments: [], unLookedLikes: [] };
  },
  created() {
    var that = this;
    axios
      .get("/customer/messages/")
      .then(function (response) {
        that.loading = false;
        that.unLookedComments = response.data.data.unLookedComments;
        that.unLookedLikes = response.data.data.unLookedLikes;
        console.log(response.data);
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    /* readLike(id) {
      var that = this;
      axios
        .get("/customer/messages/likes/" + id + "/read")
        .then(function (response) {
          console.log(response.data);
        })
        .catch(function (error) {
          console.log(error);
        });
    }, */
    readComment(id) {
      var that = this;
      axios
        .get("/customer/messages/comment/" + id + "/read")
        .then(function (response) {
          console.log(response.data);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    readAll() {
      var that = this;
      axios
        .get("/customer/messages/readAllLikes")
        .then(function (response) {
          console.log(response.data);
        })
        .catch(function (error) {
          console.log(error);
        });
      axios
        .get("/customer/messages/readAllComments")
        .then(function (response) {
          console.log(response.data);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
</style>
