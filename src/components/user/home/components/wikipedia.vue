<template>
  <div>
    <div style="width: 100%; float: left">
      <div class="ui basic segment">
        <el-row :gutter="24">
          <el-col :span="24"
            ><div class="ui basic segment">
              <el-row :gutter="24">
                <el-col :span="3">
                  <h3 style="margin-top: 5px">信息百科</h3>
                </el-col>
                <el-col :span="10"
                  ><div class="ui small fluid icon input">
                    <input type="text" placeholder="搜索..." v-model="entryQuery"/>
                    <i
                      id="entrySearch"
                      class="inverted circular search link icon"
                      @click="entrySearch"
                    ></i></div></el-col
                ><el-col :span="7" style="height: 1px"></el-col
                ><el-col :span="4"
                  ><button
                    class="ui mini right labeled icon button"
                    style="background-color: white; margin-top: 2px"
                    @click="more()"
                  >
                    <i
                      class="angle double right icon"
                      style="background-color: white"
                    ></i>
                    更多
                  </button></el-col
                >
              </el-row>
            </div></el-col
          > </el-row
        ><br />
        <el-row :gutter="24">
          <el-col :span="12">
            <div class="ui segment">
              <el-row :gutter="24" style="height: 200px">
                <el-col :span="12">
                  <img
                    class="ui fluid rounded image"
                    src="../../../../assets/message3.jpg"
                /></el-col>
                <el-col :span="12">
                  <h5 style="text-align: center">t</h5>
                  <el-descriptions>
                    <el-descriptions-item>表示测试贴</el-descriptions-item>
                  </el-descriptions></el-col
                >
              </el-row>
            </div></el-col
          >
          <el-col :span="12"
            ><div class="ui segment">
              <el-row :gutter="24" style="height: 200px">
                <el-col :span="12">
                  <img
                    class="ui fluid rounded image"
                    src="../../../../assets/message4.png"
                /></el-col>
                <el-col :span="12">
                  <h5 style="text-align: center">元宇宙</h5>
                  <el-descriptions>
                    <el-descriptions-item
                      >利用科技手段进行链接与创造的，与现实世界映射与交互的虚拟世界，具备新型社会体系的数字生活空间。元宇宙本质上是对现实世界的虚拟化、数字化过程，需要对内容生产、经济系统、用户体验以及实体...</el-descriptions-item
                    >
                  </el-descriptions></el-col
                >
              </el-row>
            </div></el-col
          > </el-row
        ><br />
        <el-row :gutter="24">
          <el-col :span="12">
            <div class="ui segment">
              <el-row :gutter="24" style="height: 200px">
                <el-col :span="12">
                  <img
                    class="ui fluid rounded image"
                    src="../../../../assets/message1.png"
                /></el-col>
                <el-col :span="12">
                  <h5 style="text-align: center">yyds</h5>
                  <el-descriptions>
                    <el-descriptions-item
                      >网络用语，永远滴神</el-descriptions-item
                    >
                  </el-descriptions></el-col
                >
              </el-row>
            </div></el-col
          >
          <el-col :span="12"
            ><div class="ui segment">
              <el-row :gutter="24" style="height: 200px">
                <el-col :span="12">
                  <img
                    class="ui fluid rounded image"
                    src="../../../../assets/message2.png"
                /></el-col>
                <el-col :span="12">
                  <h5 style="text-align: center">数学物理方法</h5>
                  <el-descriptions>
                    <el-descriptions-item
                      >数学物理方法是一门大学课程。该门课程的教学内容主要包括复变函数、数学物理方程两部分。大学课程
                      对一个物理问题的处理，通常需要三个步骤：利用物理定律将物理问题翻译成数学问题...</el-descriptions-item
                    >
                  </el-descriptions></el-col
                >
              </el-row>
            </div></el-col
          >
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import infoSearch from "../../../../request/api/information/infoSearch";

export default {
  name: "wikipedia",
  data() {
    return {
      entryQuery: ''
    }
  },
  methods: {
    more() {
      this.$router.push("/BreakingTheBarrier/literature/information");
    },
    entrySearch() {
      this.$api.infoSearch.infoSearch(this.entryQuery)
      .then(res => {
        console.log(res);
      })
      .catch()
    }
  },
};
</script>

<style scoped>
</style>
