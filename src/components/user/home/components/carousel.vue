<template>
  <el-carousel class="el-carousel">
    <el-carousel-item v-for="item in 4" :key="item">
      <h3 class="small">{{ item }}</h3>
    </el-carousel-item>
  </el-carousel>
</template>

<script>
export default {
  name: "carousel",
};
</script>

<style scoped>
.el-carousel {
  z-index: -1;
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>
