<template>
  <div class="ui container">
<!--    <router-link to="/BreakingTheBarrier/literature/information/EntryDisplay">词条展示</router-link>-->
    <router-link to=""></router-link>
    <div style="margin-bottom: 30px;">
      <sec-menu>
        <h3 slot="titleH3" style="letter-spacing: 5px;">信息百科</h3>
        <span slot="item"></span>
        <span slot="space">&nbsp;&nbsp;</span>
        <span slot="search" style="position: relative;top: -6px;height: 20px;">
          <div class="ui container" style="width: fit-content; margin: 5px;">
<!--          <button class="ui button" @click="showInput">创建词条</button>-->
          <button class="ui button">
            <router-link to="/BreakingTheBarrier/literature/information/EntryCreate">创建词条</router-link>
          </button>
        </div>
        </span>
      </sec-menu>
      <div class="ui container" style="width: fit-content;display: none;margin: 5px 0 10px 0" id="createDiv">
        <create @create="createEntry"></create>
      </div>
    </div>
    <div style="width: fit-content;margin: -20px auto 25px auto;">
    </div>

<!--词条展示-->
    <div class="ui container" style="display: inline-block">
      <entry-display
        id=""
        class="column"
        :content="approvedEntry.content">

      </entry-display>

      <hot-file class="column">
          <h3 slot="titleH3">近期竞赛</h3>
          <div slot="item" class="ui tall stacked segment" id="recentRace">
            <p>ACM国际大学生程序设计竞赛世界总决赛</p>
          </div>
          <span slot="upFile"></span>
        </hot-file>
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

    <div class="ui container">
      <update-entry :showUpdateFlag="showUpdateFlag" :id="entry.id"></update-entry>
    </div>
  </div>
</template>

<script>
  import SecMenu from "../SecMenu";
  import FileDisplay from "../FileDisplay";
  import FileDisplayCItem from "./FileDisplayCItem";
  import HotFile from "../HotFile";
  import Headbar from "../../basic/headbar";
  import Bottom from "../../basic/bottom";
  import Create from "./components/Create";
  import SearchFile from "../components/SearchFile";
  import $ from 'jquery'
  import axios from "axios";
  import ShowUnproved from "./components/ShowUnproved";
  import UpdateEntry from "./components/UpdateEntry";
  import EntryDisplay from "./components/EntryDisplay";
  export default {
    name: "Information",
    props: {
      path: String
    },
    data() {
      return {
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
          // console.log(err);
          alert(err)
        })
      //把请求到的approvedEntry存储下来
      that.$api.infoShow
        .infoShow()
        .then(res => {
          let content = res.data.data.entries.content
          for(let i in content)
            this.approvedEntry.content.push(content[i])
          // this.approvedEntry = res.data.data.entries.content
          console.log(this.approvedEntry.content);
        }).catch(err => {
        alert(err)
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
            alert(res.data.msg)
          })
          .catch(err => {
            alert(err)
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
      Bottom,
      Headbar,
      SecMenu,
      FileDisplay,
      FileDisplayCItem,
      HotFile,
      Create,
      SearchFile,
      ShowUnproved,
      UpdateEntry,
      EntryDisplay
    }
  }
</script>

<style scoped>
  div.segment p {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  div.segment {
    max-width: 254px;
  }
  #recentRace {
    max-width: 254px;
  }
  #x {
    width: 800px;
  }
</style>
