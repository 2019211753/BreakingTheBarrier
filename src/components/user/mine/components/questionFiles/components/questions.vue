<template>
  <div>
    <el-empty
      :image-size="200"
      v-if="count == 0"
      description="还未发布过内容"
    ></el-empty>
    <div class="framework" v-for="(item, index) in contentList[0]">
      <div class="frameworkBody">
        <div class="ui large feed">
          <div class="event">
<!--            <div class="label">
              <img :src="'data:image/jpg;base64,' + avatar" alt="">
            </div>
            <div class="content">
              <div class="summary">
                <a class="user"> {{ nowUser }} </a>
                <div class="date">{{ item.createTime }}</div>
              </div>
            </div>-->
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
            <!-- <i class="mail icon"></i> -->
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
  name: "questions",
  data() {
    return {avatar: sessionStorage.getItem("imgSrc"), cover: [], contentList: [], count: 0, nowUser: ""};
  },
  mounted() {
    var that = this;
    axios
      .get("/customer/personal")
      .then(function (response) {
        console.log(response.data.data.user);
        that.nowUser = response.data.data.user.nickname;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  created() {
    var that = this;
    axios
      .get("/customer/archivesQuestion")
      .then(function (response) {
        console.log(response.data);
        that.count = response.data.data.count;
        for (var year = 2021; year <= 2021; year++) {
          for (var month = 1; month <= 12; month++) {
            if (response.data.data.archiveMap[year][month]) {
              that.contentList.push(response.data.data.archiveMap[year][month]);
            }
          }
        }
        for (var i = 0; i < that.contentList[0].length; i++) {
          var url = require("../../../../../../assets/cover/cover (" +
            Math.floor(Math.random() * 10) +
            ").jpg");
          that.cover.push(url);
        }
      })
      .catch(function (error) {
        console.log(error);
      });
  },
};
</script>

<style scoped>
.framework {
  /*
  background-color: bisque; */
  margin-top: 20px;
  height: 200px;
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
