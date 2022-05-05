<template>
  <div>
    <div class="title">
      <h1 class="btb-h1">信息百科</h1>
      <div class="operate">
        <div class="ui small inverted buttons">
          <div class="ui pink inverted button" @click="turnToCreate">
            <i class="pencil alternate icon"></i>创建
          </div>
          <div class="or"></div>
          <div class="ui blue inverted button" @click="showSearchInput">
            <i class="search icon"></i>搜索
          </div>
          <div class="or"></div>
          <div class="ui red inverted button" @click="turnToCheck">
            <i class="tasks icon"></i>审核
          </div>
        </div>
      </div>
      <div class="ui icon input" id="input">
        <input type="text" placeholder="搜索..." @keyup.enter="searchEntry" v-model="query">
        <i class="inverted circular search link icon" @click="searchEntry"></i>
      </div>
    </div>
    <el-empty v-if="entries.length <= 0"></el-empty>
    <div v-else>
      <el-skeleton :rows="6" animated :loading="isLoading"/>
      <div class="mainBox">
        <div class="ui segment"
             v-for="(item,index) in entries"
             :key="index">
          <a class="ui red ribbon label">
              <span v-for="(tags,index) in item.entryTags" style="margin-right: 3px">
                {{ tags.name }}
              </span>
          </a>
          <btb-description
            class="xxx">
            <btb-description-item :label="item.title" :flag="false" icon="bookmark"
                                  class="entryBox"></btb-description-item>
            <btb-description-item
              v-if="(item.discription !== null)"
              icon="quote left"
              :flag="false">{{ item.discription }}
            </btb-description-item>
            <btb-description-item
              v-else
              icon="quote left"
              :flag="false">
              暂无简介
            </btb-description-item>
          </btb-description>
          <div @click="toEntry(item.id)" class="more">
            <btb-description-item icon="arrow circle right" label="" :flag="false"></btb-description-item>
          </div>
        </div>
      </div>
      <div class="el-page">
        <el-pagination
          class="el-page"
          layout="prev, pager, next"
          :total="pageNum"
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
  import SecMenu from "../SecMenu";
  import $ from 'jquery'
  import ShowUnproved from "./components/ShowUnproved";
  import EntryDisplay from "./components/EntryDisplay";
  import btbDescription from "../../user/home/components/btbDescription";
  import btbDescriptionItem from "../../user/home/components/btbDescriptionItem";
  export default {
    name: "Information",
    components: {
      SecMenu, ShowUnproved, EntryDisplay, btbDescription, btbDescriptionItem
    },
    data() {
      return {
        isLoading: true,
        pageNum: 0,
        entries: [],
        isShowSearch: false,
        query: ''
      }
    },
    mounted() {
      //把请求到的approvedEntry存储下来
      let pageIndex = 0;
      this.$api.infoShow
        .infoShow(pageIndex)
        .then(res => {
          this.isLoading = false
          this.pageNum = res.data.data.entries.totalPages;
          this.entries = res.data.data.entries.content
        }).catch(err => {
          this.$message.error('发生了错误')
      })
    },
    computed: {
      isActive() {
        return this.$route.path.indexOf(this.path) !== -1
      }
    },
    methods: {
      showSearchInput() {
        if (!this.isShowSearch) {
          $('#input').animate({
            top: '0',
            opacity: '1',
            // zIndex: '1'
          })
          this.isShowSearch = true
        }
        else {
          $('#input').animate({
            top: '-80px',
            opacity: '0',
            // zIndex: '-1'
          })
          this.isShowSearch = false
        }
      },
      turnToCreate() {
        this.$router.push('/BreakingTheBarrier/literature/information/EntryCreate')
      },
      toEntry(item) {
        this.$router.push(`/BreakingTheBarrier/literature/information/EntryItem?id=${item}`)
      },
      turnToCheck() {
        if(parseInt(this.$store.state.me.id) > this.$store.state.maxAdminId) {
          this.$message.warning('您不是管理员哦')
        }
        else
          this.$router.push('/BreakingTheBarrier/literature/information/EntryCheck')
      },
      searchEntry() {
        this.isLoading = true
        this.$api.infoSearch.infoSearch(this.query)
        .then(res => {
          this.isLoading = false
          if(res.data.data.entries.content.length >= 1) {
            // this.entries = []
            this.entries = res.data.data.entries.content
          }
        })
      }
    },
  }
</script>

<style scoped lang="scss">
#input {
  margin-left: 10px;
  position: relative;
  top: -80px;
  opacity: 0;
  //z-index: 1;
}
.el-page {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
.title {
  display: flex;
  flex-direction: row;
  align-items: baseline;
  padding-bottom: 5px;
  border-bottom: 1px solid grey;
  > h1 {
    margin-right: 10px;
    white-space: nowrap;
    color: grey;
  }
}
.mainBox {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  align-items: baseline;
  > div {
    width: 30%;
    height: 240px;
    .xxx {
      position: relative;
      > div {
        width: 100%;
        justify-content: center;
        border: none;
        span {
          text-overflow: ellipsis;
          overflow: hidden;
        }
      }
      :nth-child(2) {
        -webkit-line-clamp: 5;
        line-height: 150%;
      }
    }
    .more {
      position: absolute;
      bottom: 0;
      right: 0;
      cursor: pointer;
      > div {
        border: none;
      }
    }
  }
}
@media screen and (max-width: 568px){
  .mainBox > div {
    //height: 200px;
    //width: 46%;
  }
}
@media screen and (max-width: 550px){
  .mainBox > div {
    width: 100%;
  }
}
</style>
