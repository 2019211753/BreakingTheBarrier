<template>
  <div>
    <div class="framework" v-for="(item, index) in contentList">
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
          >
            <router-link
              :to="{
                path: '/helloWorld/BBS/articleContent',
                query: { articleId: item.id },
              }"
            >{{ item.title }}
            </router-link
            >
          </a
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
import axios from "axios";
/* axios.defaults.headers["token"] = sessionStorage.getItem("token"); */
export default {
  name: "articleContents",
  data() {
    return {contentList: [], pageSize: 0, cover: []};
  },
  created() {
    var that = this;
    axios
      .get("/listQuestions/?page=0")
      .then(function (response) {
        console.log(response.data);
        that.contentList = response.data.data.pages.content;
        that.pageSize = response.data.data.pages.totalPages;
        for (var i = 0; i < that.contentList.length; i++) {
          var url = require("../../../../../../assets/cover/cover (" +
            Math.floor(Math.random() * 51) +
            ").jpg");
          that.cover.push(url);
        }
        console.log(that.cover);
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
  /* background-color: bisque; */
  margin-top: 20px;
  height: 240px;
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

.el-pagination {
  margin-top: 20px;
  margin-left: 25%;
}

.ui.basic.very.padded.segment {
  font-size: 15px;
  font-weight: 900;
}
</style>
