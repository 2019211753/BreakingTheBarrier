<template>
  <div>
    <el-empty
      :image-size="200"
      v-if="count == 0"
      description="还未发布过内容"
    ></el-empty>
    <div class="framework" v-for="item in contentList[0]">
      <br />
      <div class="frameworkBody">
        <div class="ui large feed">
          <div class="event">
            <div class="label">
              <img src="../../../../../../assets/logo.png" />
            </div>
            <div class="content">
              <div class="summary">
                <a class="user"> {{ nowUser }} </a>
                <div class="date">{{ item.createTime }}</div>
              </div>
            </div>
          </div>
        </div>
        <h3 class="title">
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
        <img
          class="ui left floated image"
          src="../../../../../../assets/bg.jpg"
        />
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
  </div>
</template>

<script>
import axios from "axios";
/* axios.defaults.headers["token"] = sessionStorage.getItem("token"); */
export default {
  name: "blogs",
  data() {
    return {
      contentList: [],
      count: 0,
      nowUser: "",
    };
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
      .get("/customer/archivesBlog")
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
        console.log(that.contentList[0]);
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
</style>
