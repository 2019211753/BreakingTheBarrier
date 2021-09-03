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
      <el-aside width="250px"
        ><div class="ui large feed">
          <br />
          <div class="event">
            <div class="label">
              <img src="../../../assets/avatar.jpg" />
            </div>
            <div class="content">
              <div class="summary">
                <a>不为谁而作的歌</a>
              </div>
              <br />
              <!-- <div >
                <button class="ui circular facebook icon button">
                  <i class="facebook icon"></i>
                </button>
                <button class="ui circular twitter icon button">
                  <i class="twitter icon"></i>
                </button>
                <button class="ui circular linkedin icon button">
                  <i class="linkedin icon"></i>
                </button>
                <button class="ui circular google plus icon button">
                  <i class="google plus icon"></i>
                </button>
              </div> -->
              <div class="ui icon buttons">
                <button class="ui button">
                  <i class="align left icon"></i>
                </button>
                <button class="ui button">
                  <i class="align center icon"></i>
                </button>
                <button class="ui button">
                  <i class="align right icon"></i>
                </button>
                <button class="ui button">
                  <i class="align justify icon"></i>
                </button>
              </div>
            </div>
          </div>
          <div class="event">
            <div class="label">
              <img src="../../../assets/avatar.jpg" />
            </div>
            <div class="content">
              <div class="summary">
                <a>不为谁而作的歌</a>
              </div>
              <br />
              <!-- <div >
                <button class="ui circular facebook icon button">
                  <i class="facebook icon"></i>
                </button>
                <button class="ui circular twitter icon button">
                  <i class="twitter icon"></i>
                </button>
                <button class="ui circular linkedin icon button">
                  <i class="linkedin icon"></i>
                </button>
                <button class="ui circular google plus icon button">
                  <i class="google plus icon"></i>
                </button>
              </div> -->
              <div class="ui icon buttons">
                <button class="ui button">
                  <i class="align left icon"></i>
                </button>
                <button class="ui button">
                  <i class="align center icon"></i>
                </button>
                <button class="ui button">
                  <i class="align right icon"></i>
                </button>
                <button class="ui button">
                  <i class="align justify icon"></i>
                </button>
              </div>
            </div>
          </div></div
      ></el-aside>
      <el-main><articleSpecific></articleSpecific></el-main>
    </el-container>
  </div>
</template>

<script>
import axios from "axios";
import articleSpecific from "../../user/content/articleComponents/components/articleSpecific";
export default {
  name: "adminArticle",
  components: { articleSpecific },
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
