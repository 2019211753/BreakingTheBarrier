<template>
  <!-- <div class="ui one cards">
    <div class="ui basic segment">
      <div class="content">
        <div class="header">{{ item.nickname }}</div>
        <div class="description">{{ item.personalSignature }}</div>
      </div>
      <div class="ui bottom attached button">
        <i class="add icon"></i>
        关注
      </div>
    </div>
  </div> -->
  <div>
    <el-container
      v-for="item in userList"
      key="item.id"
      style="margin-top: 20px"
    >
      <el-aside width="500px">
        <div class="ui small five statistics">
          <div class="statistic">
            <div class="value">
              <img :src="'data:image/jpg;base64,' + item.avatar" alt="" class="ui circular inline image">
            </div>
            <div class="label">
              <a href="">{{ item.nickname }}</a>
            </div>
          </div>
          <div class="statistic">
            <div class="value">5</div>
            <div class="label">内容</div>
          </div>
          <div class="statistic">
            <div class="value">{{ item.donation }}</div>
            <div class="label">贡献度</div>
          </div>
          <div class="statistic">
            <div class="value">{{ item.followedUserNum }}</div>
            <div class="label">粉丝</div>
          </div>
          <div class="statistic">
            <div class="value">{{ item.followingUserNum }}</div>
            <div class="label">关注</div>
          </div>
        </div>
      </el-aside
      >
      <el-main
      >
        <button
          style="margin-top: -10px"
          class="ui circular red icon button"
          @click="follow(item.id)"
        >
          <i class="heart icon"></i>
        </button>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import axios from "axios";
/* axios.defaults.headers["token"] = sessionStorage.getItem("token"); */
export default {
  name: "left",
  data() {
    return {userList: []};
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
  methods: {
    follow(id) {
      var that = this;
      axios
        .get("/follow/" + id)
        .then(function (response) {
          console.log(response.data);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
</style>
