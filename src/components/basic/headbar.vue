<template>
  <div class="btb-header">
    <ul class="m-navBox">
      <li>
        <router-link to="/BreakingTheBarrier/home">
          <i class="sticky note outline icon"></i>首页
        </router-link>
      </li>
      <li>
        <router-link to="/BreakingTheBarrier/BBS/questions">
          <i class="home icon"></i>论坛
        </router-link>
      </li>
      <li>
        <router-link to="/BreakingTheBarrier/literature/data">
          <i class="server icon"></i>资料库
        </router-link>
      </li>
      <li>
        <router-link to="/BreakingTheBarrier/team">
          <i class="handshake icon"></i>组队
        </router-link>
      </li>
      <li>
        <router-link to="/BreakingTheBarrier/literature/information">
          <i class="paw icon"></i>信息百科
        </router-link>
      </li>
    </ul>
    <div class="btb-logo"><img src="../../assets/logo.png" alt="logo"></div>
    <ul class="navBox">
<!--      <li></li>-->
      <li>
        <a href="">
          <router-link to="/BreakingTheBarrier/home">
            <i class="home icon"></i>首页
          </router-link>
        </a>
      </li>
      <li>
        <a href="">
          <router-link to="/BreakingTheBarrier/BBS/questions">
            <i class="sticky note outline icon"></i>论坛
          </router-link>
        </a>
      </li>
      <li>
        <a href="">
          <router-link to="/BreakingTheBarrier/literature/data">
            <i class="server icon"></i>资料库
          </router-link>
        </a>
      </li>
      <li>
        <a href="">
          <router-link to="/BreakingTheBarrier/team">
            <i class="handshake icon"></i>组队
          </router-link>
        </a>
      </li>
      <li>
        <a href="">
          <router-link to="/BreakingTheBarrier/literature/information">
            <i class="paw icon"></i>信息百科
          </router-link>
        </a>
      </li>
    </ul>
    <div class="infoBox">
      <div style="">
        <router-link to="/BreakingTheBarrier/mine/contents/questionFiles">
          <el-skeleton :loading="loading" animated v-if="loading == true">
            <template slot="template"
            ><el-skeleton-item variant="circle image" />
            </template>
          </el-skeleton>
          <img
            v-else
            :src="$store.state.me.avatar"
            alt=""
            class="ui avatar image"
          />
        </router-link>
      </div>
      <div @click="logOut()">
        <router-link to="/"><i class="sign out icon"></i></router-link>
      </div>
    </div>
    <div class="menuBtn" @click="openMenu"><i class="align right icon"></i></div>
  </div>
</template>

<script>
export default {
  name: "headbar",
  data() {
    return {
      loading: true,
      isActive: false
    };
  },
  methods: {
    logOut() {
      sessionStorage.clear();
      history.pushState(null, null, document.URL);
      window.addEventListener("popstate", function () {
        history.pushState(null, null, document.URL);
      });
    },
    itemClass() {
      console.log(event.target);
    },
    openMenu() {
      const menu = document.querySelector('.m-navBox')
      const menuBtn = document.querySelector('.m-menu')
      if(menu.style.cssText !== '') {
        /*即当前menu已显示，应关闭显示*/
        console.log('不为空');
        menu.style.cssText = ''
      }
      else {
        console.log('为空');
        /*即当前menu未显示，应该去显示*/
        menu.style.cssText = 'z-index: 999;display: flex;'
        $('.m-navBox').animate({top: '-15px'})
      }
    }
  },
  created() {
    var that = this;
    that.$api.personalInformation
      .getPersonalInformation()
      .then(function (response) {
        that.loading = false;
        that.$store.commit("getMyAvatar", response.data.data.user.avatar);
        that.$store.commit("getMyFile1", response.data.data.user);
        that.$store.commit("getMyFile2", response.data.data);
      })
      .catch(function (error) {
        console.log(error);
      });

    window.addEventListener('resize',() => {
      if(window.innerWidth > 570 ) {
        /*>570px时使m-menu不显示*/
        const menu = document.querySelector('.m-navBox')
        menu.style.cssText = 'display: none;z-index: -1;'
      }
    })
  },
};
</script>

<style scoped lang="scss">
.menuBtn {
  display: none;
  position: absolute;
  right: 1vw;
  top: 20px;
  cursor: pointer;
  z-index: 99999;
  :hover {
    //border-bottom: 1px solid grey;
    color: #36373b;
  }
  i {
    color: gray;
  }
}
.btb-header {
  display: flex;
  flex-wrap: nowrap;
  justify-content: space-around;
  align-items: center;
  height: 100%;
  .m-navBox {
    //display: none;
    box-sizing: border-box;
    padding: 9px 10vw;
    width: 100vw;
    display: flex;
    justify-content: space-around;
    position: absolute;
    top: -80px;
    background-color: rgba(5,11,19,0.92);
    height: 67px;
    z-index: -1;
    //display: none;
    li {
      cursor: pointer;
      list-style: none;
      margin: 6vw 1vw;
      white-space: nowrap;
      a {
        color: aqua;
        font-weight: 600;
        font-size: 12px;
      }
    }
  }
  .btb-logo {
    width: 60px;
    display: flex;
    align-items: center;
    //height: 20px;
    img {
      width: 100%
    }
  }
  .navBox {
    position: relative;
    left: -14vw;
    padding-left: 0;
    display: flex;
    flex-wrap: nowrap;
    align-items: center;
    :nth-child(n+1):hover {
      border-bottom: 1px solid grey;
    }
    li {
      cursor: pointer;
      white-space: nowrap;
      list-style: none;
      margin: 0 1vw;
      a {
        display: block;
        //height: 42px;
        //padding: 0px 10px;
        font-size: 16px;
        color: grey;
        font-weight: 600;
      }
    }
  }
  .infoBox {
    display: flex;
    flex-wrap: nowrap;
    align-items: center;
    :nth-child(2) {
      margin-left: 10px;
    }
    i {
      color: gray;
    }
  }
}
@media screen and (max-width: 1163px){
  .btb-header .navBox {
    //position: relative;
    left: -10vw;
  }
  .navBox li:last-child {
    //display: none;
  }
  .btb-header .infoBox div:nth-child(2) {
    margin-left: 10px;
  }
}
@media screen and (max-width: 990px) {
  .btb-header .navBox {
    //position: relative;
    left: -5vw;
  }
  .navBox li:nth-child(5) {
    //display: none;
  }
}
@media screen and (max-width: 700px){
  .btb-header .infoBox div:nth-child(2) {
    margin-left: 5px;
  }
  .btb-header .btb-logo {
    width: 50px;
  }
  .navBox li:nth-child(4) {
    //display: none;
  }
  .btb-header .navBox li a {
    font-size: 14px;
  }
}
@media screen and (max-width: 570px){
  .btb-header .btb-logo {
    width: 30px;
  }
  .menuBtn {
    display: block;
  }
  .btb-header .navBox {
    //position: relative;
    left: -4vw;
  }
  .navBox li:nth-child(n+4):nth-child(-n+6) {
    display: none;
  }
  .btb-header .m-navBox {
    //display: flex;
    li {
      margin: 3vw 1vw;
    }
    li:nth-child(n+1) {
      //display: none;
    }
    //li:last-child {
    //  color: #0e8c8c;
    //  display: inline-block;
    //}
  }
  //.btb-header .btb-m-navBox li {
  //  margin: 3vw 1vw;
  //}
}
@media screen and (max-width: 375px) {
  .btb-header {
    .btb-logo {
      width: 30px;
    }
    .navBox {
      li:nth-child(1) {

      }
    }
  }
}
</style>
