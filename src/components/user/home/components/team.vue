<template>
  <div class="btb-container-start">
    <div class="btb-cover btb-m-container">
      <div class="border-text btb-h4">T E A M</div>
      <div class="main">
        <div class="btb-h1 header">组队招募</div>
        <div class="tip btb-h5">HAVE A COMMON GOAL</div>
        <div class="slogan">
          <div class="btb-h4">志量恢弘纳百川</div>
          <div class="btb-h4">遨游四海结英贤</div>
        </div>
        <div class="imgBox">
          <img src="@/assets/img/cover.png" alt="">
        </div>
      </div>
    </div>
    <div class="postsList">
      <div class="ui segment">
        <a class="ui red ribbon label">大学生创新创业</a>
        <btb-description>
          <div>
            <btb-description-item
              label="发布者" icon="address book">koo</btb-description-item>
            <btb-description-item
              label="联系" icon="linechat">QQ2322134363</btb-description-item>
            <btb-description-item
              label="时间" icon="clock">
              2021/09/07
            </btb-description-item>
            <btb-description-item
              label="备注" icon="clipboard list">
              <el-tag size="small">沙河</el-tag>
              <el-tag size="small">编程</el-tag>
            </btb-description-item>
          </div>
          <div>
            <btb-description-item label="需求" icon="film">
              已有成型的产品想法，需要坚持不懈的小伙伴，有编程经验优先
              ● 善于学习,愿意分享,享受编程; ● 具备良好的产品意识和用户体验意识; 4. 优先条件: ●有js框架设计和开发经验; ● 有前端组件库设计建设经验;
            </btb-description-item>
          </div>
        </btb-description>
      </div>
      <div class="ui segment">
        <a  :class="2>=1 ? 'ui orange right ribbon label ' : 'ui red ribbon label'">美国大学生数学建模</a>
        <btb-description>
          <div>
            <btb-description-item
              label="发布者" icon="address book">薛定谔想不出方程</btb-description-item>
            <btb-description-item
              label="联系" icon="linechat">wx19801175389</btb-description-item>
            <btb-description-item
              label="时间" icon="clock">
              2021/09/21
            </btb-description-item>
            <btb-description-item
              label="备注" icon="clipboard list">
              <el-tag size="small">沙河</el-tag>
              <el-tag size="small">本部</el-tag>
            </btb-description-item>
          </div>
          <div>
            <btb-description-item label="需求" icon="film">
              求大佬带带
            </btb-description-item>
          </div>
        </btb-description>
      </div>
      <div class="ui segment">
        <a class="ui red ribbon label">蓝俱杯</a>
        <btb-description>
          <div>
            <btb-description-item
              label="发布者" icon="address book">小兰今天吃什么</btb-description-item>
            <btb-description-item
              label="联系" icon="linechat">tel18198755576</btb-description-item>
            <btb-description-item
              label="时间" icon="clock">
              2021/12/04
            </btb-description-item>
            <btb-description-item
              label="备注" icon="clipboard list">
              <el-tag size="small">北京邮电大学</el-tag>
            </btb-description-item>
          </div>
          <div>
            <btb-description-item label="希望" icon="film">
              团队合作能力强，及时沟通不要不说话，能力第二态度第一，不能摆烂
            </btb-description-item>
          </div>
        </btb-description>
      </div>
      <div class="ui segment">
        <a class="ui orange right ribbon label">唱邮心声帮帮唱</a>
        <btb-description>
          <div>
            <btb-description-item
              label="发布者" icon="address book">kiki</btb-description-item>
            <btb-description-item
              label="联系" icon="linechat">QQ675797754</btb-description-item>
            <btb-description-item
              label="时间" icon="clock">
              2021/12/11
            </btb-description-item>
            <btb-description-item
              label="备注" icon="clipboard list">
              <el-tag size="small">沙河</el-tag>
            </btb-description-item>
          </div>
          <div>
            <btb-description-item label="希望" icon="film">
              有没有唱林俊杰的歌的小伙伴
            </btb-description-item>
          </div>
        </btb-description>
      </div>
      <div class="ui segment">
        <a class="ui red ribbon label">ICPC</a>
        <btb-description>
          <div>
            <btb-description-item
              label="发布者" icon="address book">猫猫麻完了</btb-description-item>
            <btb-description-item
              label="联系" icon="linechat">tel18100876095</btb-description-item>
            <btb-description-item
              label="时间" icon="clock">
              2021/12/04
            </btb-description-item>
            <btb-description-item
              label="备注" icon="clipboard list">
              <el-tag size="small">北京邮电大学</el-tag>
            </btb-description-item>
          </div>
          <div>
            <btb-description-item label="希望" icon="film">
              希望 leetcodetop100做完; 语言 c++、python都可
            </btb-description-item>
          </div>
        </btb-description>
      </div>
    </div>
  </div>
</template>

<script>
import btbDescription from "./btbDescription";
import btbDescriptionItem from "./btbDescriptionItem";
export default {
  name: "team",
  components: { btbDescription, btbDescriptionItem },
  mounted() {
    // const tagList = document.querySelectorAll('div.postsList > div.segment')
    // console.log(tagList);
  },
  computed: {
  },
  methods: {
    more() {
      this.$router.push("/BreakingTheBarrier/team");
    },
  },
};
</script>

<style scoped lang="scss">
.btb-cover {
  border: 4px solid;
  border-color: #161616  #161616 #fcc55c #fcc55c;
  box-sizing: border-box;
  position: relative;
  width: 40%;
  .border-text {
    display: flex;
    flex-direction: column;
    position: absolute;
    top: 50%;
    left: -12px;
    writing-mode: vertical-rl;
    letter-spacing: 2px;
    background-color: white;
    padding: 10px 0;
  }
  .main {
    /* padding: 10px 10px 5px 10px; */
    padding: 1vw 0.3vw;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    div {
      display: flex;
      justify-content: center;
    }
    .header {
      //font-size: 25px;
      font-weight: 200;
      color: bisque;
    }
    .tip {
      white-space: nowrap;
      //font-size: 12px;
      letter-spacing: 1px;
      margin: 5px 0;
    }
    .slogan {
      width: 85%;
      flex-direction: column;
      margin: 10px 0 20px 0;
      letter-spacing: 0.4vw;
      font-weight: 500;
      border-left: 3px solid bisque;
      div {
        margin: 2px 0;
        white-space: nowrap;
      }
    }
    .imgBox {
      width: 62.5%;
      img {
        width: 100%;
      }
    }
  }
}
.right {
  background-color: #0e8c8c;
}
@media screen and (max-width: 530px) {
  .btb-cover .main .tip {
    display: none;
  }
  .btb-cover .main .slogan {
    width: 100%;
  }
}
</style>
