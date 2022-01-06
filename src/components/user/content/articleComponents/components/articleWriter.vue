<template>
  <div class="ui basic segment">
    <div class="ui segment" v-if="loading == true">
      <el-skeleton :loading="loading" animated>
        <template slot="template"
          ><el-row>
            <el-col :span="8" style="height: 1px"></el-col
            ><el-col :span="8"
              ><el-skeleton-item
                variant="circle image"
                style="margin: auto; width: 70px; height: 70px"
            /></el-col>
            <el-col :span="8"></el-col>
          </el-row>
          <el-skeleton-item variant="text" /><el-skeleton-item
            variant="text"
          /><el-skeleton-item variant="text" />
        </template>
      </el-skeleton>
      <div class="ui divider"></div>
      <el-skeleton-item variant="text" /><el-skeleton-item variant="text" />
    </div>
    <div class="ui segment" v-else>
      <img
        class="ui tiny centered circular image"
        :src="'data:image/jpg;base64,' + avatar"
      />
      <h3 style="text-align: center">
        <a href=""
          ><router-link
            v-if="nowUser == posterUserId0"
            to="/helloWorld/mine/contents/questionFiles"
            >{{ nickname }} </router-link
          ><router-link
            v-else
            :to="{
              path: '/helloWorld/visitor/questions',
              query: { userId0: posterUserId0 },
            }"
            >{{ nickname }}
          </router-link></a
        >
      </h3>
      <p style="color: grey">{{ personalSignature }}</p>
      <div class="button">
        <div
          class="ui button"
          @click="follow(posterUserId0)"
          :class="following == true ? userFollowing : userUnFollowing"
        >
          关注
        </div>
      </div>
      <div class="ui divider"></div>
      <div class="ui three mini statistics">
        <div class="statistic">
          <div class="value">{{ contentsNum }}</div>
          <div class="label">内容</div>
        </div>
        <div class="statistic">
          <div class="value">{{ followedNum }}</div>
          <div class="label">粉丝</div>
        </div>
        <div class="statistic">
          <div class="value">{{ followingNum }}</div>
          <div class="label">关注</div>
        </div>
      </div>
    </div>
    <div class="ui segment">
      <h3>作者提问</h3>
      <div class="ui divider"></div>
      <div class="ui feed">
        <div
          class="event"
          v-for="(item, index) in questions"
          v-if="index < 5"
          :key="index"
        >
          <div class="label">
            <img :src="'data:image/jpg;base64,' + avatar" />
          </div>
          <div class="content">
            <div class="summary">
              <a class="user" href="">
                <router-link
                  :to="{
                    path: '/helloWorld/BBS/articleContent',
                    query: {
                      articleId: item.id,
                      posterUserId0: item.posterUserId0,
                    },
                  }"
                  >{{ item.title }}
                </router-link>
              </a>
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
  </div>
</template>

<script>
import axios from "axios";
/* axios.defaults.headers["token"] = sessionStorage.getItem("token"); */
export default {
  name: "articleWriter",
  data() {
    return {
      loading: true,
      avatar: "",
      nickname: "",
      personalSignature: "",
      contentsNum: "",
      followedNum: "",
      followingNum: "",
      questions: "",
      following: "",
      userFollowing: "ui red button",
      userUnFollowing: "ui button",
      posterUserId0: this.$route.query.posterUserId0,
    };
  },
  created() {
    var that = this;
    axios
      .get("/visit/" + this.$route.query.posterUserId0)
      .then(function (response) {
        console.log(response.data);
        that.loading = false;
        that.avatar = response.data.data.userInfo.avatar;
        that.nickname = response.data.data.userInfo.nickname;
        that.personalSignature = response.data.data.userInfo.personalSignature;
        that.contentsNum =
          response.data.data.blogs.length + response.data.data.questions.length;
        that.followedNum = response.data.data.userInfo.followedUserNum;
        that.followingNum = response.data.data.userInfo.followingUserNum;
        that.questions = response.data.data.questions;
        that.following = response.data.data.following;
        /* --------------------------------------- */
        sessionStorage["posterUser"] = response.data.data.userInfo.nickname;
        sessionStorage["posterUserAvatar"] = response.data.data.userInfo.avatar;
        sessionStorage["posterUserPersonalSignature"] =
          response.data.data.userInfo.personalSignature;
        sessionStorage["posterUserContentsNum"] =
          response.data.data.blogs.length +
          response.data.data.comments.length +
          response.data.data.questions.length;
        sessionStorage["posterUserFollowingUserNum"] =
          response.data.data.userInfo.followingUserNum;
        sessionStorage["posterUserFollowedUserNum"] =
          response.data.data.userInfo.followedUserNum;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    follow() {
      var that = this;
      axios
        .get("/follow/" + this.$route.query.posterUserId0)
        .then(function (response) {
          console.log(response.data);
          if (response.data.code == 406) {
            that.$message({
              message: response.data.msg,
              type: "warning",
            });
          } else {
            that.following = response.data.data.following;
            that.$message({
              message: response.data.msg,
              type: "success",
            });
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
p {
  text-align: center;
}

.button {
  width: 100px;
  margin: auto;
}
</style>
