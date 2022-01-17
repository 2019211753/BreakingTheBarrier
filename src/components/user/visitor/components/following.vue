<template>
  <div class="ui segment">
    <el-empty
      :image-size="200"
      v-if="!list.length && loading == false"
      description="暂无关注者"
    ></el-empty>
    <div class="ui large feed">
      <div class="event" v-for="item in list">
        <div class="label">
          <img :src="item.avatar" alt="" />
        </div>
        <div class="content">
          <div class="summary">
            <a @click="turnToOthers(item.id)"> {{ item.nickname }} </a>
            <div class="date">{{ item.personalSignature }}</div>
            <div class="buttons">
              <button class="ui disabled mini blue icon button">
                <i class="envelope icon"></i>
              </button>
              <button class="ui mini icon button" @click="follow(item.id)">
                <i class="heart icon"></i>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <el-skeleton :loading="loading" v-if="loading == true" animated :count="5">
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
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "following",
  data() {
    return { loading: true, list: [] };
  },
  created() {
    var that = this;
    axios
      .get("/visit/" + this.$route.query.userId0)
      .then(function (response) {
        console.log(response.data);
        /* that.loading = false; */
        that.list = response.data.data.followingUsers;
        that.loading = false;
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
          if (response.data.code == 406) {
            that.$message({
              message: response.data.msg,
              type: "warning",
            });
          } else {
            that.$store.commit(
              "getMyFollowingNum",
              response.data.data.myFollowingNum
            );
            that.$message({
              message: response.data.msg,
              type: "success",
            });
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    turnToOthers(id) {
      var that = this;
      axios
        .get("/visit/" + id)
        .then(function (response) {
          console.log(response.data);
          that.$store.commit("getOthersFile", response.data.data);
          if (id == that.$store.state.me.id) {
            that.$router.push("/helloWorld/mine/contents/questionFiles");
          } else {
            that.$router.push({
              path: "/helloWorld/visitor/questions",
              query: { userId0: id },
            });
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
.buttons {
  float: right;
}
</style>
