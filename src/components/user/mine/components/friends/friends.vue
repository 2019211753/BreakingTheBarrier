<template>
  <div class="framework">
    <div>
      <div class="ui button" @click="turnToFollowing()">关注</div>
      <div class="ui button" @click="turnToFollowed()">粉丝</div>
    </div>
    <div class="main"><router-view></router-view></div>
  </div>
</template>

<script>
export default {
  name: "friends",
  methods: {
    turnToFollowing() {
      var that = this;
      that.$router.push("/helloWorld/mine/friends/following");
    },
    turnToFollowed() {
      var that = this;
      that.$router.push("/helloWorld/mine/friends/followed");
    },
  },
};
</script>

<style scoped>
.framework {
  margin-top: 10px;
}
.main {
  margin-top: 10px;
}
</style>
