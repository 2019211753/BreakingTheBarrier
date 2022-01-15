<template>
  <div class="ui basic segment">
    <div class="ui basic segment">
      <h3 style="margin-top: 10px">
        资料库<button
          class="ui mini right labeled icon button"
          style="margin-left: 720px; background-color: white"
          @click="more()"
        >
          <i
            class="angle double right icon"
            style="background-color: white"
          ></i>
          更多
        </button>
      </h3>
    </div>
    <el-row :gutter="24">
      <el-col :span="4">
        <div class="ui basic segment" style="height: 120px">
          <img class="ui rounded image" src="../../../../assets/P.png" alt="" />
        </div>
      </el-col>
      <el-col :span="5">
        <div class="ui basic segment" style="height: 120px">
          <img class="ui rounded image" src="../../../../assets/a.jpg" alt="" />
        </div>
      </el-col>
      <el-col :span="5">
        <div class="ui basic segment" style="height: 120px">
          <img class="ui rounded image" src="../../../../assets/p.jpg" alt="" />
        </div>
      </el-col>
      <el-col :span="5">
        <div class="ui basic segment" style="height: 120px">
          <img
            class="ui rounded image"
            src="../../../../assets/ppt.jpg"
            alt=""
          />
        </div>
      </el-col>
      <el-col :span="5">
        <div class="ui basic segment" style="height: 120px">
          <img class="ui rounded image" src="../../../../assets/x.jpg" alt="" />
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
      this.$router.push("/helloWorld/database");
    },
  },
};
</script>

<style scoped>
</style>
