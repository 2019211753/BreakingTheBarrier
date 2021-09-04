<template>
  <div>
    <el-empty :image-size="200" v-if="list" description="暂无粉丝"></el-empty>
    <div class="ui large feed">
      <div class="event" v-for="item in list">
        <div class="label">
          <img src="../../../../../../assets/avatar.jpg" />
        </div>
        <div class="content">
          <div class="summary">
            <a>Jenny Hess</a>
            <div class="date">3 days ago</div>
            <div class="ui icon buttons">
              <button class="ui button"><i class="align left icon"></i></button>
              <button class="ui button">
                <i class="align center icon"></i>
              </button>
              <button class="ui button">
                <i class="align right icon"></i>
              </button>
              <button class="ui button">
                <i class="align justify icon"></i>
              </button>
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
  name: "followed",
  data() {
    return { list: [] };
  },
  created() {
    var that = this;
    axios
      .get("/customer/followInfo")
      .then(function (response) {
        console.log(response.data);
        that.list = response.data.data.followedUsers;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
};
</script>

<style scoped>
.ui.feed {
  margin-top: 10px;
}
.event {
  margin-top: 10px;
}
.buttons {
  float: right;
}
</style>
