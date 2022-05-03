<template>
  <div class="body">
    <button id="openHotBtn" class="ui mini blue button" @click="openHot"><i class="hotjar icon"></i></button>
    <div class="mask" style="display: block"></div>
    <div class="contents">
      <router-view></router-view>
    </div>
    <div class="hot" id="hot">
      <hotArticle></hotArticle>
      <div class="ui basic segment">
        <div class="ui segment">
          <div class="ui fluid input">
            <input type="text" placeholder="搜索..." v-model="searchContent" />
            <router-link
              :to="{
                path: '/BreakingTheBarrier/BBS/searchArticleResult',
                query: { searchContent: this.searchContent },
              }"
            >
              <button class="ui teal button" v-if="this.searchContent">
                搜索
              </button>
            </router-link>
          </div>
          <br />
          <div class="ui teal fluid button" @click="turnToAddArticle()">
            发布内容
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import $ from 'jquery'
import hotArticle from "./components/hotArticle";
export default {
  name: "article",
  components: { hotArticle },
  data() {
    return {
      searchContent: "" ,
      isShow: false,
      moreThan550: true
    };
  },
  mounted() {
    window.addEventListener('resize',() => {
      let mask = document.getElementsByClassName('mask')[0]
      let hot = document.getElementsByClassName('hot')[0]
      if(window.innerWidth > 550) {
        mask.style.cssText = 'opacity: 0;z-index:-1'
        hot.style.cssText = ''
      }
      else
        mask.style.cssText = ''
    })
  },
  methods: {
    turnToAddArticle() {
      var that = this;
      that.$router.push("/BreakingTheBarrier/BBS/article/addArticle");
    },
    openHot() {
      if (!this.isShow) {
        $('div.hot').animate({
          right: '0',
          opacity: 1
        })
        $('div.mask').animate({
          opacity: '1'
        })
        this.isShow = true
      }
      else if(this.isShow){
        $('div.hot').animate({
          right: '-380px',
          opacity: 0
        })
        $('div.mask').animate({
          opacity: '0'
        })
        this.isShow = false
      }
    }
  },
};
</script>

<style scoped lang="scss">
.body {
  position: relative;
  display: flex;
  justify-content: space-around;
  //width: 1200px;
  margin: 0 auto;
  box-sizing: border-box;
  padding: 0 120px;
  > button.button {
    z-index: 2;
    top: 0;
    display: none;
    margin-bottom: 5px;
  }
  .mask {
    position: fixed;
    z-index: 1;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background-color: rgba(38, 50, 56,.8);
    opacity: 0;
  }
  .contents {
    margin-right: 30px;
  }
  .hot {
    width: 300px;
    z-index: 2;
  }
}
@media screen and (max-width: 989px){
  .body {
    padding: 0;
  }
  .body .hot {
    width: 30%;
  }
}
@media screen and (max-width: 668px){
  .body .contents {
    margin-right: 0;
  }
}
@media screen and (max-width: 550px){
  .body {
    flex-direction: column;
    > button.button {
      display: block;
    }
    .hot {
      width: 100%;
      position: absolute;
      top: 33px;
      right: -380px;
      opacity: 0;
    }
  }
}
/*.body {*/
/*  width: 80%;*/

/*  margin: auto;*/
/*  margin-top: 20px;*/
/*}*/
/*.main {*/
/*  width: 64%;*/
/*  float: left;*/
/*}*/
/*.hot {*/
/*  width: 35%;*/
/*  float: left;*/
/*  margin-left: 1%;*/
/*}*/
</style>
