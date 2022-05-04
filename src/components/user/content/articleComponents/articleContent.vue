<template>
  <div class="body">
    <button id="openHotBtn" class="ui mini blue button" @click="openHot"><i class="id card icon"></i></button>
    <div class="mask" style="display: block"></div>
    <div class="main">
      <div class="specific">
        <articleSpecific></articleSpecific>
      </div>
    </div>
    <div class="side">
      <div class="writer">
        <articleWriter></articleWriter>
      </div>
    </div>
  </div>
</template>

<script>
import articleWriter from "./components/articleWriter";
import articleSpecific from "./components/articleSpecific";
import $ from "jquery";

export default {
  name: "articleContent",
  components: { articleSpecific, articleWriter },
  mounted() {
    window.addEventListener('resize',() => {
      if(this.$route.path.lastIndexOf('articleContent') !== -1) {
        let mask = document.getElementsByClassName('mask')[0]
        let hot = document.getElementsByClassName('side')[0]
        if(window.innerWidth > 550) {
          mask.style.cssText = 'opacity: 0;z-index:-1'
          hot.style.cssText = ''
        }
        else
          mask.style.cssText = ''
      }
    })
  },
  methods: {
    openHot() {
      let mask = document.getElementsByClassName('mask')[0]
      if (!this.isShow) {
        mask.style.cssText = 'z-index: 1'
        $('div.side').animate({
          top: '72px',
          opacity: '1',
          zIndex: '2'
        })
        $('div.mask').animate({
          opacity: '1'
        })
        this.isShow = true
      }
      else if(this.isShow){
        $('div.side').animate({
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
  }
};
</script>

<style scoped lang="scss">
.body {
  //width: 73vw;
  margin: auto;
  display: flex;
  flex-direction: row;
  min-height: 80vh;
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
  .main {
    margin-top: 20px;
    width: 73%;
    .specific {
      width: 98%;
      margin: auto;
    }
  }
  .side {
    margin-top: 20px;
    width: 27%;
    //width: 300px;
    z-index: 2;
    .writer {
      margin: auto;
      width: 100%;
    }
  }
}

@media screen and (max-width: 700px){
  .body {
    flex-direction: column;
    align-items: center;
    width: 100%;
    > button.button {
      display: block;
      width: 100%;
    }
    .main {
      width: 100%;
      margin-top: 0;
    }
    .side {
      width: 95%;
      position: absolute;
      top: 0;
      //right: -380px;
      opacity: 0;
      z-index: -1;
    }
  }

}
</style>
