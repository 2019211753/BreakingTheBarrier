<template>
  <div>
    <div class="title">
      <h1 class="btb-h1">信息百科</h1>
      <div class="operate">
        <div class="ui small inverted buttons">
          <div class="ui pink inverted button"><i class="pencil alternate icon"></i>创建</div>
          <div class="or"></div>
          <div class="ui blue inverted button" @click="showSearchInput">
            <i class="search icon"></i>搜索
          </div>
        </div>
      </div>
      <div class="ui icon input" id="input">
        <input type="text" placeholder="搜索...">
        <i class="inverted circular search link icon"></i>
      </div>
    </div>
<!--    <button class="ui button">-->
<!--      <router-link to="/BreakingTheBarrier/literature/information/EntryCreate">创建词条</router-link>-->
<!--    </button>-->
<!--词条展示-->
    <div class="mainBox">
      <div class="ui segment"
        v-for="(item,index) in approvedEntry.content"
           :key="index">
        <a class="ui red ribbon label">
              <span v-for="(tags,index) in item.entryTags">
                {{ tags.name }}
              </span>
        </a>
        <btb-description
          class="xxx">
          <btb-description-item :label="item.title" :flag="false" icon="bookmark" class="entryBox"></btb-description-item>
          <btb-description-item
            v-if="(item.discription !== null)"
            icon="quote left"
            :flag="false">{{item.discription}}
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
        layout="prev, pager, next"
        :total="pageNum"
        hide-on-single-page>
      </el-pagination>
    </div>
<!--展示未审核-->
    <div class="" style="display: none">
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
        isShowSearch: false
      }
    },
    mounted() {
      //请求到的entry对象储存到data里
      // let that = this
      // that.$api.infoGetUnapro
      //   .infoGetUnapro()
      //   .then(res => {
      //         let content = res.data.data.entries.content
      //         for(let i in content) {
      //           this.entry.content.push(content[i])
      //         }
      //         // console.log(content);
      //       })
      //   .catch(err => {
      //     console.log(err);
      //     // alert(err)
      //   })
      //把请求到的approvedEntry存储下来
      let pageIndex = 0;
      this.$api.infoShow
        .infoShow(pageIndex)
        .then(res => {
          let content = res.data.data.entries.content
          this.pageNum = res.data.data.entries.totalPages;
          // console.log(res.data.data.entries.totalPages);
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
      showInput() {
        $('#createDiv')
          .transition('scale')
        ;
      },
      toEntry(item) {
        console.log('点击');
        this.$router.push(`/BreakingTheBarrier/literature/information/EntryItem?id=${item}`)
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
.title {
  display: flex;
  flex-direction: row;
  align-items: baseline;
  padding-bottom: 5px;
  border-bottom: 1px solid grey;
  > h1 {
    margin-right: 10px;
    white-space: nowrap;
  }
}
.mainBox {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  align-items: baseline;
  > div {
    width: 30%;
    height: 235px;
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
    height: 200px;
  }
}
</style>
