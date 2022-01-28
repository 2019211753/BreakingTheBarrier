<template>
  <div>
    <div>
      <h1>信息百科</h1>
      <div class="ui icon input">
        <input type="text" placeholder="搜索..." />
        <i class="inverted circular search link icon"></i>
      </div>
    </div>
    <div class="main">
      <div class="wiki"></div>
      <div class="message"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "wikipedia",
};
</script>

<style scoped>
h1 {
  margin-top: 20px;
  float: left;
}
.ui.input {
  width: 30%;
  margin-top: 20px;
  margin-left: 20px;
  float: left;
}
.main {
  height: 999px;
  margin-top: 20px;
  background-color: aqua;
}
.wiki {
  margin-left: 0px;
  width: 64%;
  float: left;
  height: 200px;
  background-color: rgb(102, 26, 224);
}
.message {
  width: 35%;
  float: left;
  height: 200px;
  background-color: rgb(255, 0, 0);
  margin-left: 1%;
}
</style>
