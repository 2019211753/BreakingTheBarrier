<template>
  <div class="ui basic segment">
    <el-carousel class="btb-carousel" style="width: 80%; margin: auto">
      <el-carousel-item>
        <img
          class="ui fluid rounded image"
          src="../../../../assets/cover/cover (7).jpg"
        />
      </el-carousel-item>
      <el-carousel-item>
        <img
          class="ui fluid rounded image"
          src="../../../../assets/cover/cover (8).jpg"
        />
      </el-carousel-item>
      <el-carousel-item>
        <img
          class="ui fluid rounded image"
          src="../../../../assets/cover/cover (9).jpg"
        />
      </el-carousel-item>
      <el-carousel-item>
        <img
          class="ui fluid rounded image"
          src="../../../../assets/cover/cover (10).jpg"
        />
      </el-carousel-item>
      <el-carousel-item>
        <img
          class="ui fluid rounded image"
          src="../../../../assets/cover/cover (11).jpg"
        />
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
export default {
  name: "carousel",
};
</script>

<style lang="scss">
.btb-carousel {
  height: 36vw;
  max-height: 300px;
  ul {
    display: flex;
    justify-content: space-around;
    width: 100%;
    padding: 0 20%;
    li {
      width: 20%;
      display: flex;
      justify-content: center;
    }
  }

}
</style>
