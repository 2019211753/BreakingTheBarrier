<template>
  <div>
    <div class="ui segment">
      <img
        class="ui centered medium circular image"
        src="../assets/hello.jpg"
        alt=""
      />
      <div class="ui fluid button" @click="turnToLogIn()">登录</div>
      <div class="ui fluid button" @click="turnToRegister()">注册</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "helloWorld",
  methods: {
    turnToLogIn() {
      this.$router.push("/helloWorld/logIn");
    },
    turnToRegister() {
      this.$router.push("/helloWorld/register");
    },
  },
};
</script>

<style scoped>
.ui.segment {
  width: 33%;
  margin: auto;
  margin-top: 10%;
}
.ui.button {
  margin-top: 20px;
}
</style>
