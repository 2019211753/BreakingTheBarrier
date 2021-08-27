<template>
  <div class="ui one cards">
    <div class="card" v-for="item in userList">
      <div class="content">
        <div class="header">{{ item.nickname }}</div>
        <div class="description">{{ item.personalSignature }}</div>
        <div class="ui small statistics">
          <div class="statistic">
            <div class="value">
              <img
                src="../../../../assets/avatar.jpg"
                class="ui circular inline image"
              />
            </div>
          </div>
          <div class="statistic">
            <div class="value">22</div>
            <div class="label">内容</div>
          </div>
          <div class="statistic">
            <div class="value">{{ item.donation }}</div>
            <div class="label">贡献度</div>
          </div>
          <div class="statistic">
            <div class="value">0{{ item.followed }}</div>
            <div class="label">粉丝</div>
          </div>
          <div class="statistic">
            <div class="value">0{{ item.following }}</div>
            <div class="label">关注</div>
          </div>
        </div>
      </div>
      <div class="ui bottom attached button">
        <i class="add icon"></i>
        关注
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
axios.defaults.headers["token"] = sessionStorage.getItem("token");
export default {
  name: "left",
  data() {
    return { userList: [] };
  },
  created() {
    var that = this;
    axios
      .get("/rank")
      .then(function (response) {
        console.log(response.data);
        that.userList = response.data.data.users;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
};
</script>

<style scoped>
</style>
