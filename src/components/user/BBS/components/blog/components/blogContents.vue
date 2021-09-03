<template>
  <div>
    <div class="framework" v-for="item in contentList">
      <br />
      <div class="frameworkBody">
        <div class="ui large feed">
          <div class="event">
            <div class="label">
              <img src="../../../../../../assets/logo.png" />
            </div>
            <div class="content">
              <div class="summary">
                <a class="user"> {{ item.nickname }} </a>
                <div class="date">{{ item.createTime }}</div>
              </div>
            </div>
          </div>
        </div>
        <h3 class="title">
          <a href=""
            ><router-link
              :to="{
                path: '/BBS/content',
                query: { articleId: item.id },
              }"
              >{{ item.title }}</router-link
            ></a
          >
        </h3>
        <img class="ui left floated image" src="../../../../../../assets/bg.jpg" />
        <p>
          {{ item.description }}
        </p>
        <div>
          <a class="ui label" v-for="tags in item.tags">
            <!-- <i class="mail icon"></i> -->
            {{ tags.name }}
          </a>
        </div>
      </div>
      <br />
      <br />
    </div>
    <el-pagination
      class="el-pagination"
      layout="prev, pager, next"
      @current-change="handleCurrentChange"
      :page-count="pageSize"
    >
    </el-pagination>
  </div>
</template>

<script>
import axios from "axios";
axios.defaults.headers["token"] = sessionStorage.getItem("token");
export default {
  name: "blogContents",
  data() {
    return { contentList: [], pageSize: 0 };
  },
  created() {
    var that = this;
    axios
      .get("/listQuestions/?page=0")
      .then(function (response) {
        console.log(response.data);
        that.contentList = response.data.data.pages.content;
        that.pageSize = response.data.data.pages.totalPages;
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
.el-pagination {
  margin-top: 20px;
  margin-left: 25%;
}
</style>
