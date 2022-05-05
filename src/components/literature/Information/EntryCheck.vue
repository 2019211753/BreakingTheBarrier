<template>
  <div>
    <el-empty v-if="entry.length <= 0"></el-empty>
    <div v-else>
      <el-skeleton :rows="6" animated :loading="isLoading"/>
      <div class="title">
        <h1 class="btb-h1">信息百科|词条审核</h1>
      </div>
      <div class="mainBox">
        <div class="ui segment"
             v-for="(item,index) in entry"
             :key="index">
          <a class="ui red ribbon label">
              <span v-for="(tags,index) in item.entryTags">
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
          <div class="operate">
            <div class="ui red inverted button" @click="entryPass(item.id,index)">
              <i class="check icon"></i>通过
            </div>
            <div class="ui pink inverted button" @click="entryReject(item.id,index)">
              <i class="x icon"></i>拒绝
            </div>
          </div>
        </div>
      </div>
      <div class="el-page">
        <el-pagination
          @current-change="handlePageChange"
          layout="prev, pager, next"

          :page-count="pageNum"
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
  name: "EntryCheck",
  components: {
    SecMenu, ShowUnproved, EntryDisplay, btbDescription, btbDescriptionItem
  },
  data() {
    return {
      entry: [],
      pageNum: 0,
      pageIndex: 0,
      isLoading: true
    }
  },
  created() {
    if (parseInt(this.$store.state.me.id) > this.$store.state.maxAdminId) {
      this.$message.warning('您不是管理员哦')
    }
    else
      this.getUnapprovedEntry(this.pageIndex)//初始请求第0页的数据
  },
  methods: {
    entryPass(entryId,index) {
      this.$api.infoApprove.entryApprove(entryId)
      .then(res => {
        this.$message.success('已通过')
        this.entry.splice(index,1)
      })
      .catch(err => {
        this.$message.error(err)
      })
    },
    entryReject(entryId,index) {
      this.$api.infoDisapprove.entryDisapprove(entryId)
        .then(res => {
          this.$message.success('已拒绝')
          this.entry.splice(index,1)
        })
        .catch(err => {
          this.$message.error(err)
        })
    },
    handlePageChange(currentPage) {
      this.getUnapprovedEntry(currentPage - 1)
    },
    toEntry(item) {
      console.log('点击');
      this.$router.push(`/BreakingTheBarrier/literature/information/EntryItem?id=${item}`)
    },
    getUnapprovedEntry(pageIndex) {
      // 请求到的entry对象储存到data里
      this.$api.infoGetUnapro
        .infoGetUnapro(pageIndex)
        .then(res => {
          this.isLoading = false
          this.entry = res.data.data.entries.content
          this.pageNum = res.data.data.entries.totalPages
        })
        .catch(err => {
          this.$message.error(err)
        })
    }
  }
}
</script>

<style scoped lang="scss">
.operate {
  display: flex;
  justify-content: space-between;
  white-space: nowrap;
  position: absolute;
  bottom: 5px;
  width: 83%;
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
    width: 45%;
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
    width: 46%;
  }
}
@media screen and (max-width: 550px){
  .mainBox > div {
    width: 100%;
  }
}
</style>
