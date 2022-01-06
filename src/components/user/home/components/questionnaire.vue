<template>
  <div>
    <div class="header"><div class="font">问卷广场</div></div>
    <el-row :gutter="24"> </el-row>
    <el-row :gutter="24">
      <el-col :span="4"
        ><div class="grid-content bg-purple" style="height: 120px"></div
      ></el-col>
      <el-col :span="5"
        ><div class="grid-content bg-purple" style="height: 120px"></div
      ></el-col>
      <el-col :span="5"
        ><div class="grid-content bg-purple" style="height: 120px"></div
      ></el-col>
      <el-col :span="5"
        ><div class="grid-content bg-purple" style="height: 120px"></div
      ></el-col>
      <el-col :span="5"
        ><div class="grid-content bg-purple" style="height: 120px"></div
      ></el-col>
    </el-row>
  </div>
</template>

<script>
import carousel from "./carousel";
export default {
  name: "questionnaire",
  components: { carousel },
};
</script>

<style scoped>
</style>
