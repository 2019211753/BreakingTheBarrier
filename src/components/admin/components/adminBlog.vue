<template>
  <div>
    <div class="ui icon input">
      <input type="text" placeholder="搜索..." @click="search()" />
      <i class="inverted circular search link icon"></i>
    </div>
    <div class="ui icon input">
      <input type="text" placeholder="搜索..." />
      <i class="inverted circular search link icon"></i>
    </div>
    <div class="ui icon input">
      <input type="text" placeholder="搜索..." />
      <i class="inverted circular search link icon"></i>
    </div>
    <el-container>
      <el-aside width="250px">Aside</el-aside>
      <el-main>Main</el-main>
    </el-container>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "adminBlog",
  methods: {
    search() {
      var that = this;
      axios
        .post("/admin/searchQuestions", { title: "test" })
        .then(function (response) {
          console.log(response.data);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
.el-aside {
  background-color: aqua;
}
</style>
