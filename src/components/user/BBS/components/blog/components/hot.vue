<template>
  <div class="framework">
    <div class="frameworkBody">
      <br />
      <h3 class="ui left floated header">热门话题</h3>
      <div class="ui clearing divider"></div>
      <div>
        <div class="ui large feed">
          <div class="event" v-for="item in impactList">
            <div class="label">
              <img src="../../../../../../assets/logo.png" />
            </div>
            <div class="content">
              <!-- <div class="date">{{ item.createTime }}</div> -->
              <div class="summary">
                <router-link
                  :to="{
                    path: '/BBS/content',
                    query: { articleId: item.id },
                  }"
                  >{{ item.title }}</router-link
                >
              </div>
            </div>
          </div>
        </div>
      </div>
      <br />
    </div>
  </div>
</template>

<script>
import axios from "axios";
axios.defaults.headers["token"] = sessionStorage.getItem("token");
export default {
  name: "hot",
  data() {
    return { impactList: [] };
  },
  created() {
    var that = this;
    axios
      .get("/listBlogs")
      .then(function (response) {
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
