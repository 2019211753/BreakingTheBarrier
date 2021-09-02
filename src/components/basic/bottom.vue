<template>
  <div class="bottom">
    <br />
    <div class="message">
      <img class="ui small right floated image" src="../../assets/wechat.png" />
      <h3 class="ui header">联系我们</h3>
      <div class="ui hidden divider">
        扫描右边二维码提出您的宝贵意见，么么哒
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "bottom",
};
</script>

<style scoped>
.bottom {
  width: 80%;
  height: 200px;
  margin: auto;
  background-color: rgb(204, 200, 207);

  /*  margin-bottom: 0px; */
}
.message {
  width: 90%;
  margin: auto;
}
</style>
