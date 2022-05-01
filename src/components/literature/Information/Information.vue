<template>
  <div class="ui container">
<!--    <router-link to="/BreakingTheBarrier/literature/information/EntryDisplay">词条展示</router-link>-->
<!--    <router-link to=""></router-link>-->
    <div style="margin-bottom: 30px;">
      <sec-menu>
        <h3 slot="titleH3" style="letter-spacing: 5px;">信息百科</h3>
        <span slot="item"></span>
        <span slot="space">&nbsp;&nbsp;</span>
        <span slot="search" style="position: relative;top: -6px;height: 20px;">
          <div class="ui container" style="width: fit-content; margin: 5px;">
          <button class="ui button">
            <router-link to="/BreakingTheBarrier/literature/information/EntryCreate">创建词条</router-link>
          </button>
        </div>
        </span>
      </sec-menu>
    </div>

<!--词条展示-->
    <div class="main-box">
      <entry-display
        class="info-box"
        id=""
        :content="approvedEntry.content">

      </entry-display>

    </div>
    <div class="el-page">
      <el-pagination
        layout="prev, pager, next"
        :total="pageNum"
        hide-on-single-page>
      </el-pagination>
    </div>
<!--展示未审核-->
    <div class="ui container">
      <h2>未审核词条</h2>
      <ul>
        <li v-for="(item, index) in entry.content"
            :key="index">
          <show-unproved
            @showUpdateInput="showUpdateInput"
            :content="item"
            v-if="!item.approved">
          </show-unproved>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
  import SecMenu from "../SecMenu";
  import FileDisplay from "../FileDisplay";
  import FileDisplayCItem from "./FileDisplayCItem";
  import HotFile from "../HotFile";
  import SearchFile from "../components/SearchFile";
  import $ from 'jquery'
  import ShowUnproved from "./components/ShowUnproved";
  import EntryDisplay from "./components/EntryDisplay";
  export default {
    name: "Information",
    props: {
      path: String
    },
    data() {
      return {
        pageNum: 0,
        entry: {
          content: [],
          id: 0
        },
        approvedEntry: {
          content: [],
        },
        flag: 1,
        showUpdateFlag: false,
      }
    },
    mounted() {
      //请求到的entry对象储存到data里
      let that = this
      that.$api.infoGetUnapro
        .infoGetUnapro()
        .then(res => {
              let content = res.data.data.entries.content
              for(let i in content) {
                this.entry.content.push(content[i])
              }
              // console.log(content);
            })
        .catch(err => {
          console.log(err);
          // alert(err)
        })
      //把请求到的approvedEntry存储下来
      let pageIndex = 0;
      that.$api.infoShow
        .infoShow(pageIndex)
        .then(res => {
          let content = res.data.data.entries.content
          this.pageNum = res.data.data.entries.totalPages;
          console.log(res.data.data.entries.totalPages);
          for(let i in content)
            this.approvedEntry.content.push(content[i])
          // this.approvedEntry = res.data.data.entries.content
          // console.log(this.approvedEntry.content);
        }).catch(err => {
          this.$message.error('发生了错误')
        // alert(err)
        // console.log(err);
      })
    },
    computed: {
      isActive() {
        return this.$route.path.indexOf(this.path) !== -1
      }
    },
    methods: {
      showInput() {
        $('#createDiv')
          .transition('scale')
        ;
      },
      createEntry(title, newContent) {
        let data = {
          'title': title,
          'newContent': newContent
        }
        let that = this
        that.$api.infoCreate
          .createEntry(data)
          .then(res => {
            console.log(res);
            // alert(res.data.msg)
            console.log(res.data.msg);
          })
          .catch(err => {
            // alert(err)
            console.log(err);
          })
      },
      showUpdateInput(id) {
        // console.log('监听到子组件（ShowUnproved）的‘更新’按钮点击');
        this.showUpdateFlag = !this.showUpdateFlag
        // console.log(id);
        this.entry.id = id
      },
      btnClick() {
        $('.ui.sidebar')
          .sidebar('toggle');
      }
    },

    components: {
      SecMenu,
      FileDisplay,
      FileDisplayCItem,
      HotFile,
      SearchFile,
      ShowUnproved,
      EntryDisplay
    }
  }
</script>

<style scoped>
  .main-box {
    display: flex;
    width: 1200px;
    margin: 0 auto;
    box-sizing: border-box;
    /*justify-content: flex-end;*/
  }
  .info-box {
    display: flex;
    width: 775px;
    flex-wrap: wrap;
    justify-content: space-around;
    box-sizing: border-box;
    border: 1px solid #ededed;
    border-radius: 5px;
  }
  .hot-box {
    width: 400px;
    box-sizing: border-box;
    border: 1px solid #ededed;
    border-radius: 5px;
  }
</style>
