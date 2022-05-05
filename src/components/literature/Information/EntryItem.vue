<template>
  <div>
    <el-skeleton :rows="6" animated :loading="isLoading"/>
    <div class="mainBox">
      <div class="title">
        <header><i class="heading icon"></i></header>
        <h1 class="content">{{entry.title}}</h1>
      </div>
      <div class="operate">
        <div class="ui small inverted buttons">
          <div class="ui red inverted button" @click="editEntry"><i class="edit icon"></i>编辑</div>
          <div class="or"></div>
          <div class="ui yellow inverted button" @click="deleteEntry"><i class="eraser icon"></i>删除</div>
          <div class="or"></div>
          <div class="ui pink inverted button" @click="turnToCreate"><i class="pencil alternate icon"></i>创建</div>
        </div>
      </div>
      <div class="alias">
        <header><i class="book icon"></i>别名</header>
        <div class="content">{{ entry.alias }}</div>
      </div>
      <div class="tags">
        <header><i class="tags icon"></i></header>
        <el-tag v-if="item.name !== undefined"
          v-for="(item,index) in entry.entryTags" :key="index">{{item.name}}</el-tag>
        <el-tag v-else>暂无</el-tag>
      </div>
      <div class="description">
        <header><i class="info icon"></i>简述</header>
        <div class="content">{{ entry.discription }}</div>
      </div>
      <div class="currentContent">
        <header><i class="bars icon"></i>正文</header>
        <div class="content">
          <v-md-preview-html :html="entry.currentContent" preview-class="vuepress-markdown-body">
          </v-md-preview-html>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import editor from "../../user/BBS/components/editor/editor";
  export default {
    name: "EntryItem",
    components: {editor},
    data() {
      return {
        id: '',
        entry: '',
        isLoading: true
      }
    },
    created() {
      this.id = this.$route.query.id
      this.$api.infoSearchById.infoSearchById(this.id)
      .then(res => {
        this.entry = res.data.data.entries
        this.isLoading = false
      })
      .catch(err => {
        this.$message.error(err)
      })
    },
    methods: {
      editEntry() {

      },
      deleteEntry() {
        if(parseInt(this.$store.state.me.id) > this.$store.state.maxAdminId) {
          this.$
        }
      },
      turnToCreate() {

      }
    },
  }
</script>

<style scoped lang="scss">
  .mainBox {
    display: flex;
    flex-direction: column;
    color: grey;
    header {
      display: flex;
      flex-wrap: nowrap;
      white-space: nowrap;
      margin-right: 10px;
    }
    .content {
      margin-bottom: 10px;
      letter-spacing: 1px;
      line-height: 20px;
      color: #316ea2;
    }
    > div {
      margin-bottom: 10px;
    }
    .title {
      font-size: 25px;
      font-weight: 600;
      display: flex;
      flex-direction: row;
      align-items: baseline;
      .content {
        color: #316ea2;
        cursor: pointer;
        padding-bottom: 10px;
        line-height: 36px;
        font-size: 36px;
      }
      h1:hover {
        border-bottom: 3px solid #316ea2;
      }

    }
    .operate {
      margin-bottom: 40px;
    }
    .alias {
      display: flex;
      font-size: 14px;
      .content {
        color: #316ea2;
        cursor: pointer;
        padding-bottom: 5px;
      }
      .content:hover {
        border-bottom: 1px solid #316ea2;
      }
    }
    .tags {
      display: flex;
      flex-direction: row;
      align-items: baseline;
      > span {
        margin-left: 5px;
      }
    }
    .description {
      display: flex;
      flex-direction: column;
      .content {
        padding: 5px 20px;
      }
    }
    .currentContent {
      display: flex;
      flex-direction: column;
    }

  }
</style>
