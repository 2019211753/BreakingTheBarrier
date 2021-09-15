<template>
  <div>
    <el-empty
      :image-size="200"
      v-if="!list.length"
      description="暂无关注者"
    ></el-empty>
    <div class="ui large feed">
      <div class="event" v-for="item in list">
        <div class="label">
          <img :src="'data:image/jpg;base64,' + item.avatar" alt="">
        </div>
        <div class="content">
          <div class="summary">
            <a>{{ item.nickname }}</a>
            <div class="date">{{ item.personalSignature }}</div>
            <div class="buttons">
              <button class="ui disabled circular blue icon button">
                <i class="envelope icon"></i>
              </button>
              <button
                class="ui circular blue icon button"
                @click="unfollow(item.id)"
              >
                <i class="heart icon"></i>
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
  name: "following",
  data() {
    return { list: [] };
  },
  created() {
    var that = this;
    axios
      .get("/customer/followInfo")
      .then(function (response) {
        console.log(response.data);
        that.list = response.data.data.followingUsers;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  methods: {
    unfollow(id) {
      var that = this;
      axios
        .get("/follow/" + id)
        .then(function (response) {
          console.log(response.data);
          for (var i = 0; i < that.list.length; i++) {
            if (that.list[i].id == id) {
              that.list.pop(that.list[i]);
            }
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
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
