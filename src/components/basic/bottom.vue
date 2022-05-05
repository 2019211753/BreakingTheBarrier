<template>
  <div class="bottom">
    <div class="text">
      <h3 class="btb-h3">联系我们</h3>
      <div>
        All these things shall love do unto you that you may know the secrets of your heart,and in that knowledge become a fragment of Life’s heart.
      </div>
    </div>
    <div class="qrcode">
      <img src="../../assets/img/contact/qrCode.png" alt="">
    </div>
  </div>
</template>

<script>
export default {
  name: "bottom",
};
</script>

<style scoped lang="scss">
.bottom {
  //height: 200px;
  //margin: auto;
  //width: 80%;
  display: flex;
  justify-content: space-between;
  color: grey;
  margin-bottom: 20px;
  box-sizing: border-box;
  .text {
    text-transform: uppercase;
    letter-spacing: 1px;
    font-size: 13px;
    width: 80%;
  }
  .qrcode {
    width: 65px;
    height: 65px;
    box-shadow: rgb(1 1 1 / 16%) 6px 6px 0 1px;
    img {
      width: 100%;
      height: 100%;
    }
  }
}
@media screen and (max-width: 550px){
  .bottom .text {
    font-size: 12px;
  }
}
</style>
