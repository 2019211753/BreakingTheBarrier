<template>
  <div class="ui container">
    <div style="margin-bottom: 30px;">
      <sec-menu>
        <h3 slot="titleH3" style="letter-spacing: 5px;">信息百科</h3>
        <span slot="item"></span>
        <span slot="space">&nbsp;&nbsp;</span>
        <span slot="search" style="position: relative;top: -6px;height: 20px;">
          <div class="ui container" style="width: fit-content; margin: 5px;">
          <button class="ui button" @click="showInput">创建词条</button>
        </div>
        </span>
      </sec-menu>
      <div class="ui container" style="width: fit-content;display: none;margin: 5px 0 10px 0" id="createDiv">
        <create @create="createEntry"></create>
      </div>
    </div>
    <div style="width: fit-content;margin: -20px auto 25px auto;">
<!--      <search-file @search="search"></search-file>-->
    </div>
    <file-display style="margin:0 5px"></file-display>
    <hot-file>
          <h3 slot="titleH3">近期竞赛</h3>
          <div slot="item" class="ui tall stacked segment" id="recentRace">
            <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames
              ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor
              sit amet, ante. Donec eu libero sit amet quam egestas semper. Aenean ultricies mi
              vitae est. Mauris placerat eleifend leo.</p>
          </div>
          <span slot="upFile"></span>
        </hot-file>

<!--展示未审核-->
    <div class="ui container">
      <h2>未审核词条</h2>
      <ul>
        <li v-for="(item, index) in entry.content"
            :key="index">
          <show-unproved
            @showUpdateInput="showUpdateInput"
            :content="item.title"
            :id="item.id"
            v-if="!item.approved">
          </show-unproved>
        </li>
      </ul>
    </div>

    <div class="ui container">
      <update-entry :showUpdateFlag="showUpdateFlag"></update-entry>
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
  import Update from "./components/Update";
  import SearchFile from "../components/SearchFile";
  import $ from 'jquery'
  import axios from "axios";
  import ShowUnproved from "./components/ShowUnproved";
  import UpdateEntry from "./components/UpdateEntry";
  export default {
    name: "Information",
    props: {
      path: String
    },
    data() {
      return {
        entry: {
          content: [],
        },
        flag: 1,
        showUpdateFlag: false
      }
    },
    mounted() {
      //请求到的entry对象储存到data里
      axios('infoEntry/unapprovedEntries',{
      }).then(res => {
        let content = res.data.data.entries.content
        for(let i in content) {
          this.entry.content.push(content[i])
        }
        // console.log(content);
      })
      .catch(err => {
        console.log(err);
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
        axios.post('infoEntry/create', {
          'title': title,
          'newContent': newContent
        }).then(res => {
          // console.log(res);
        }).catch(err => {
          alert(err)
        })
      },
      showUpdateInput() {
        // console.log('监听到子组件的‘更新’按钮点击');
        this.showUpdateFlag = !this.showUpdateFlag
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
      Update,
      SearchFile,
      ShowUnproved,
      UpdateEntry
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
</style>
