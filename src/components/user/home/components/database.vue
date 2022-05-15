<template>
  <div class="ui basic segment">
    <div class="btb-header">
      <h3 class="btb-h3">
        资料库
      </h3>
      <button
        class="ui mini right labeled icon button"
        style="background: none"
        @click="more()"
      >
        <i
          class="angle double right icon"
          style="background-color: white"
        ></i>
        更多
      </button>
    </div>
    <el-row type="flex" justify="space-around">
      <el-col >
        <div class="imgBox">
          <img class="" src="../../../../assets/P.png" alt="" />
        </div>
      </el-col>
      <el-col >
        <div class="imgBox">
          <img class="" src="../../../../assets/p.jpg" alt="" />
        </div>
      </el-col>
      <el-col >
        <div class="imgBox">
          <img class="" src="../../../../assets/x.jpg" alt="" />
        </div>
      </el-col>
      <el-col >
        <div class="imgBox">
          <img class="" src="../../../../assets/ppt.jpg" alt="" />
        </div>
      </el-col>
      <el-col >
        <div class="imgBox">
          <img class="" src="../../../../assets/x.jpg" alt="" />
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "database",
  methods: {
    more() {
      this.$router.push("/BreakingTheBarrier/literature");
    },
  },
};
</script>

<style scoped lang="scss">
img {
  width: 100%;
}
.btb-header {
  display: flex;
  flex-wrap: nowrap;
  justify-content: space-between;
  h3 {
    //font-size: 16px;
  }
}
.imgBox {
  //width: 20%;
  padding: 5px 1vw;
}
</style>
