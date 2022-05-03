<template>
  <div class="body">
    <button id="openHotBtn" class="ui mini blue button" @click="openHot"><i class="hotjar icon"></i></button>
    <div class="mask" style="display: block"></div>
    <div class="contents">
      <router-view></router-view>
    </div>
    <div class="hot" id="hot">
      <hotArticle></hotArticle>
      <div class="mainBox">
        <div class="ui icon input">
          <input type="text" placeholder="搜索..." v-model="searchContent">
          <i class="inverted circular search link icon" @click="turnToRes"></i>
        </div>
        <button class="ui right labeled blue icon button" @click="turnToAddArticle()">
          <i class="right arrow icon"></i>
          发布内容
        </button>
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
    turnToRes() {
      this.$router.push(`/BreakingTheBarrier/BBS/searchArticleResult?searchContent=${this.searchContent}`)
    },
    openHot() {
      let mask = document.getElementsByClassName('mask')[0]
      if (!this.isShow) {
        mask.style.cssText = 'z-index: 1'
        $('div.hot').animate({
          top: '30px',
          opacity: '1',
          zIndex: '2'
        })
        $('div.mask').animate({
          opacity: '1'
        })
        this.isShow = true
      }
      else if(this.isShow){
        $('div.hot').animate({
          top: '-520px',
          opacity: 0
        })
        $('div.mask').animate({
          opacity: '0',
          zIndex: '-1'
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
    z-index: 10;
    top: 0;
    display: none;
    margin-bottom: 5px;
  }
  .mask {
    position: fixed;
    z-index: -1;
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
  .mainBox {
    display: flex;
    flex-direction: column;
    > div {
      margin: 5px 0;
    }
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
      top: 0px;
      //right: -380px;
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
