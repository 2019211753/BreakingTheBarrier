<template>
  <div>
    <div class="ui icon input">
      <input type="text" placeholder="搜索..." />
      <i class="inverted circular search link icon" @click="search()"></i>
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
      <el-aside width="300px">Aside</el-aside>
      <el-main>Main</el-main>
    </el-container>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "adminUser",
  methods: {
    search() {
      var that = this;
      axios
        .post("/admin/searchUser", { nickname: "山水夜止" })
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
  background-color: rgb(34, 14, 218);
}
</style>
