<template>
  <div>
    <div class="header"><div class="font">问卷广场</div></div>
    <div class="ui grid">
      <div class="four column row">
        <div class="column"><carousel></carousel></div>
        <div class="column"><carousel></carousel></div>
        <div class="column"><carousel></carousel></div>
        <div class="column"><carousel></carousel></div>
      </div>
    </div>
    <!--  <div
      style="

        height: 200px;

        background-color: red;

      "
    ></div>
    <div
      style="
        width: 33%;
        height: 200px;
        margin-left: 3px;
        background-color: red;
        float: left;
      "
    ></div>
    <div
      style="
        width: 33%;
        height: 200px;
        margin-left: 3px;
        background-color: red;
        float: left;
      "
    ></div> -->
    <!-- <el-carousel trigger="click" class="el-carousel">
      <el-carousel-item v-for="item in 4" :key="item">
        <div class="ui three cards">
          <div class="ui card">
            <div class="image">
              <img src="../../../../assets/logo.png" height="200" width="200" />
            </div>
            <div class="content">
              <a class="header">Kristy</a>
              <div class="meta">
                <span class="date">Joined in 2013</span>
              </div>
              <div class="description">
                Kristy is an art director living in New York.
              </div>
            </div>
            <div class="extra content">
              <a>
                <i class="user icon"></i>
                22 Friends
              </a>
            </div>
          </div>
          <div class="ui card">
            <div class="image">
              <img src="../../../../assets/logo.png" height="200" width="200" />
            </div>
            <div class="content">
              <a class="header">Kristy</a>
              <div class="meta">
                <span class="date">Joined in 2013</span>
              </div>
              <div class="description">
                Kristy is an art director living in New York.
              </div>
            </div>
            <div class="extra content">
              <a>
                <i class="user icon"></i>
                22 Friends
              </a>
            </div>
          </div>
          <div class="ui card">
            <div class="image">
              <img src="../../../../assets/logo.png" height="200" width="200" />
            </div>
            <div class="content">
              <a class="header">Kristy</a>
              <div class="meta">
                <span class="date">Joined in 2013</span>
              </div>
              <div class="description">
                Kristy is an art director living in New York.
              </div>
            </div>
            <div class="extra content">
              <a>
                <i class="user icon"></i>
                22 Friends
              </a>
            </div>
          </div>
        </div>
      </el-carousel-item>
    </el-carousel> -->
  </div>
</template>

<script>import carousel from "./carousel";
export default {
  name: "questionnaire",
  components:{carousel}
};
</script>

<style scoped>
.header {
  height: 40px;
  background-color: rgb(100, 133, 208);
  margin: auto;
  width: 100%;
  margin-top: 50px;
}
.font {
  font-size: 16px;
  color: white;
  font-weight: 600;
  padding: 0 10px;
  line-height: 42px;
  font-size: 16px;
}
.grid {
  width: 100%;
  margin: auto;
}
.column {
  height: 300px;

}
/* .el-carousel {
  width: 70%;
  margin: auto;
  margin-top: 20px;
} */
</style>
