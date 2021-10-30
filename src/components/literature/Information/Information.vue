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
      <div class="ui container" style="width: fit-content;display: none" id="createDiv">
        <create @create="createEntry"></create>
      </div>
    </div>
    <div style="width: fit-content;margin: -20px auto 25px auto;">
<!--      <search-file @search="search"></search-file>-->
    </div>
    <file-display></file-display>
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

  </div>
</template>

<script>
  import SecMenu from "../SecMenu";
  import FileDisplay from "../FileDisplay";
  import HotFile from "../HotFile";
  import Headbar from "../../basic/headbar";
  import Bottom from "../../basic/bottom";
  import Create from "./components/Create";
  import Update from "./components/Update";
  import SearchFile from "../components/SearchFile";
  import $ from 'jquery'
  import axios from "axios";
  export default {
    name: "Information",
    props: {
      path: String
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
          console.log(res);
        }).catch(err => {
          alert(err)
        })
      },
    },

    components: {
      Bottom,
      Headbar,
      SecMenu,
      FileDisplay,
      HotFile,
      Create,
      Update,
      SearchFile
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
