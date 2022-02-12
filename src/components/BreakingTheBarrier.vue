<template>
  <div>
    <div class="ui basic segment">
      <img
        class="ui centered medium circular image"
        src="../assets/logo.jpg"
        alt=""
      />
      <div class="ui teal fluid button" @click="turnToLogIn()">登录</div>
      <div class="ui teal fluid button" @click="turnToRegister()">注册</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "BreakingTheBarrier",
  methods: {
    turnToLogIn() {
      this.$router.push("/BreakingTheBarrier/logIn");
    },
    turnToRegister() {
      this.$router.push("/BreakingTheBarrier/register");
    },
  },
};
</script>

<style scoped>
.ui.segment {
  width: 350px;
  margin: auto;
  margin-top: 200px;
}
.ui.button {
  margin-top: 20px;
}
</style>
