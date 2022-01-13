<template>
  <div class="ui basic segment">
    <div class="ui segment">
      <div class="frameworkBody">
        <h3 class="ui left floated header">热门话题</h3>
        <el-skeleton
          style="width: 240px"
          :loading="loading"
          animated
          :count="5"
        >
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
        <div>
          <div class="ui large feed">
            <div class="event" v-for="item in impactList">
              <div class="label">
                <img :src="'data:image/jpg;base64,' + item.avatar" alt="" />
              </div>
              <div class="content">
                <!-- <div class="date">{{ item.createTime }}</div> -->
                <div class="summary">
                  <router-link
                    :to="{
                      path: '/helloWorld/BBS/blogContent',
                      query: {
                        blogId: item.id,
                        posterUserId0: item.posterUserId0,
                      },
                    }"
                    >{{ item.title }}</router-link
                  >
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
/* axios.defaults.headers["token"] = sessionStorage.getItem("token"); */
export default {
  name: "hotBlog",
  data() {
    return { loading: true, impactList: [] };
  },
  created() {
    var that = this;
    axios
      .get("/listBlogs")
      .then(function (response) {
        that.loading = false;
        that.impactList = response.data.data.impacts;
        console.log(that.impactList);
      })
      .catch(function (error) {
        console.log(error);
      });
  },
};
</script>

<style scoped>
.framework {
  width: 100%;
  /*  height: 200px; */
  /* background-color: bisque; */
}
.frameworkBody {
  width: 90%;
  /* height: 200px; */
  margin: auto;
}
</style>
