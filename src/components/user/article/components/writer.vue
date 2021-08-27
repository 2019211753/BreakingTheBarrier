<template>
  <div>
    <img
      class="ui tiny centered circular image"
      src="../../../../assets/logo.png"
    />
    <h3>dongwushenhua</h3>
    <p>helloWorld!</p>

    <div class="button">
      <div
        class="ui button"
        @click="follow()"
        :class="following == true ? userFollow : button"
      >
        关注
      </div>
    </div>
    <div class="ui divider"></div>
    <div class="ui three mini statistics">
      <div class="statistic">
        <div class="value">22</div>
        <div class="label">内容</div>
      </div>
      <div class="statistic">
        <div class="value">22</div>
        <div class="label">粉丝</div>
      </div>
      <div class="statistic">
        <div class="value">22</div>
        <div class="label">关注</div>
      </div>
    </div>
    <br />
    <h3>作者博客</h3>
    <div class="ui divider"></div>
    <div class="ui feed">
      <div class="event">
        <div class="label">
          <img src="../../../../assets/logo.png" />
        </div>
        <div class="content">
          <div class="summary">
            <a class="user">不为谁而作的歌 </a>
          </div>
        </div>
      </div>
      <div class="event">
        <div class="label">
          <img src="../../../../assets/logo.png" />
        </div>
        <div class="content">
          <div class="summary">
            <a class="user">小半 </a>
          </div>
        </div>
      </div>
      <div class="event">
        <div class="label">
          <img src="../../../../assets/logo.png" />
        </div>
        <div class="content">
          <div class="summary">
            <a class="user">鼓楼 </a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
axios.defaults.headers["token"] = sessionStorage.getItem("token");
export default {
  name: "writer",
  data() {
    return { following: "", userFollow: "ui red button", button: "ui button" };
  },
  created() {
    var that = this;
    axios
      .get("/question/" + this.$route.query.articleId)
      .then(function (response) {
        that.template = response.data.data.template;
        console.log(that.template);
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    follow() {
      var that = this;
      axios
        .get("/follow/1")
        .then(function (response) {
          console.log(response.data);
          that.following = response.data.data.following;
          that.$message({
            message: response.data.msg,
            type: "success",
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
h3 {
  text-align: center;
}
p {
  text-align: center;
}
.button {
  width: 100px;
  margin: auto;
}
</style>
