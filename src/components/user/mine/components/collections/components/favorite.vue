<template>
  <div>
    <el-empty
      :image-size="200"
      v-if="!questions.length && !blogs.length"
      description="收藏夹为空"
    ></el-empty>
    <div class="framework" v-for="(item, index) in questions">
      <div class="frameworkBody">
        <div class="ui large feed">
          <div class="event">
            <div class="label">
              <img :src="'data:image/jpg;base64,' + avatar" alt="">
            </div>
            <div class="content">
              <div class="summary">
                <a class="user"> {{ item.nickname }} </a>
                <div class="date">{{ item.createTime }}</div>
              </div>
            </div>
          </div>
        </div>
        <h3 class="title" style="margin-top: -10px">
          <a href=""
            ><router-link
              :to="{
                path: '/helloWorld/BBS/blogContent',
                query: { blogId: item.id },
              }"
              >{{ item.title }}</router-link
            ></a
          >
        </h3>
        <div style="width: 100%; height: 130px">
          <img
            class="ui left floated image"
            style="height: 80%"
            :src="cover[index]"
          />
          <div class="ui basic very padded segment">
            {{ item.description }}
          </div>
        </div>
        <div style="margin-top: -10px">
          <a class="ui label" v-for="tags in item.tags">
            <!-- <i class="mail icon"></i> -->
            {{ tags.name }}
          </a>
        </div>
      </div>
    </div>
    <div class="framework" v-for="(item, index) in blogs">
      <br />
      <div class="frameworkBody">
        <div class="ui large feed">
          <div class="event">
            <div class="label">
              <img :src="'data:image/jpg;base64,' + item.avatar" alt="">
            </div>
            <div class="content">
              <div class="summary">
                <a class="user"> {{ item.nickname }} </a>
                <div class="date">{{ item.createTime }}</div>
              </div>
            </div>
          </div>
        </div>
        <h3 class="title" style="margin-top: -10px">
          <a href=""
            ><router-link
              :to="{
                path: '/helloWorld/BBS/articleContent',
                query: { articleId: item.id },
              }"
              >{{ item.title }}</router-link
            ></a
          >
        </h3>
        <div style="width: 100%; height: 130px">
          <img
            class="ui left floated image"
            style="height: 80%"
            :src="cover[index]"
          />
          <div class="ui basic very padded segment">
            {{ item.description }}
          </div>
        </div>
        <div style="margin-top: -10px">
          <a class="ui label" v-for="tags in item.tags">
            {{ tags.name }}
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
/* axios.defaults.headers["token"] = sessionStorage.getItem("token"); */
export default {
  name: "favorite",
  data() {
    return {
      avatar:sessionStorage.getItem("imgSrc"),
      cover: [],
      questions: [],
      blogs: [],
      nowUser: sessionStorage.getItem("nickname"),
    };
  },
  created() {
    var that = this;
    axios
      .get("/customer/favorite/" + this.$route.query.favoriteId)
      .then(function (response) {
        console.log(response.data);
        that.questions = response.data.data.questions.content;
        that.blogs = response.data.data.blogs.content;
        for (var i = 0; i < that.questions.length; i++) {
          var url = require("../../../../../../assets/cover/cover (" +
            Math.floor(Math.random() * 51) +
            ").jpg");
          that.cover.push(url);
        }
        for (var i = 0; i < that.blogs.length; i++) {
          var url = require("../../../../../../assets/cover/cover (" +
            Math.floor(Math.random() * 51) +
            ").jpg");
          that.cover.push(url);
        }
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    handleCurrentChange(val) {
      var that = this;
      var nowPage = val - 1;
      axios
        .get("/listQuestions/?page=" + nowPage)
        .then(function (response) {
          console.log(response.data);
          that.contentList = response.data.data.pages.content;
          that.pageSize = response.data.data.pages.totalPages;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
.framework {
  margin-top: 20px;
  height: 260px;
}
.frameworkBody {
  width: 90%;
  margin: auto;
}
.title {
  margin-top: 0px;
}
.ui.label {
  margin-top: 5px;
}
img {
  height: 120px;
}
.ui.basic.very.padded.segment {
  font-size: 15px;
  font-weight: 900;
}
</style>
