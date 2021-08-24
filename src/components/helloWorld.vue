<template>
  <div>
    <div class="ui segment">
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
  width: 40%;
  margin: auto;
}
.ui.button {
  margin-top: 20px;
}
</style>
