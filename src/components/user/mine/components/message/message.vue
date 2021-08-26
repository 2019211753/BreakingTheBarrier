<template>
  <div>
    <div class="ui large feed">
      <h3 class="ui dividing header">未读消息</h3>
      <div
        class="event"
        v-if="unLookedComments"
        v-for="item in unLookedComments"
      >
        <div class="label">
          <img src="../../../../../assets/avatar.jpg" />
        </div>
        <div class="content">
          <div class="summary">
            <a class="user"> {{ item.nickname }} </a> 评论了你：
            <a href=""
              ><router-link
                :to="{
                  path: '/BBS/article',
                  query: { articleId: item.questionId0 },
                }"
              >
                {{ item.content }}</router-link
              ></a
            >.
            <div class="date">{{ item.createTime }}</div>
          </div>
        </div>
        <div class="ui icon button" @click="readComment(item.id)">
          <i class="red circle icon"></i>
        </div>
      </div>
      <div class="event" v-if="unLookedLikes" v-for="item in unLookedLikes">
        <div class="label">
          <img src="../../../../../assets/avatar.jpg" />
        </div>
        <div class="content">
          <div class="summary">
            <a class="user"> {{ item.nickname }} </a> 点赞了你的：
            <a href=""
              ><router-link
                :to="{
                  path: '/BBS/article',
                  query: { articleId: item.questionId0 },
                }"
              >
                a</router-link
              ></a
            >.
            <div class="date">{{ item.createTime }}</div>
          </div>
        </div>
        <div class="ui icon button" @click="readLike(item.id)">
          <i class="red circle icon"></i>
        </div>
      </div>
      <div
        class="ui right floated button"
        v-if="unLookedLikes && unLookedComments"
        @click="readAll()"
      >
        一键已读
      </div>
      <br />
      <h3 class="ui dividing header">历史消息</h3>
      <div class="event" v-if="lookedComments" v-for="item in lookedComments">
        <div class="label">
          <img src="../../../../../assets/avatar.jpg" />
        </div>
        <div class="content">
          <div class="summary">
            <a class="user"> {{ item.nickname }} </a> 评论了你：
            <a href=""
              ><router-link
                :to="{
                  path: '/BBS/article',
                  query: { articleId: item.questionId0 },
                }"
              >
                {{ item.content }}</router-link
              ></a
            >.
            <div class="date">{{ item.createTime }}</div>
          </div>
        </div>
      </div>
      <div class="event" v-if="lookedLikes" v-for="item in lookedLikes">
        <div class="label">
          <img src="../../../../../assets/avatar.jpg" />
        </div>
        <div class="content">
          <div class="summary">
            <a class="user"> {{ item.nickname }} </a> 点赞了你的：
            <a href=""
              ><router-link
                :to="{
                  path: '/BBS/article',
                  query: { articleId: item.questionId0 },
                }"
              >
                a</router-link
              ></a
            >.
            <div class="date">{{ item.createTime }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "message",
  data() {
    return {
      lookedComments: [],
      lookedLikes: [],
      unLookedComments: [],
      unLookedLikes: [],
    };
  },
  created() {
    var that = this;
    axios
      .get("/customer/messages/")
      .then(function (response) {
        that.lookedComments = response.data.data.lookedComments;
        that.lookedLikes = response.data.data.lookedLikes;
        that.unLookedComments = response.data.data.unLookedComments;
        that.unLookedLikes = response.data.data.unLookedLikes;
        console.log(response.data);
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    readLike(id) {
      var that = this;
      axios
        .get("/customer/messages/likes/" + id + "/read")
        .then(function (response) {
          console.log(response.data);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
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
.ui.feed {
  margin-top: 20px;
}
.ui.icon.button {
  background-color: white;
}
</style>
