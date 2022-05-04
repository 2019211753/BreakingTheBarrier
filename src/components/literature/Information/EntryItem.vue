<template>
  <div>
    <el-skeleton :rows="6" animated :loading="isLoading"/>
    <div class="mainBox">
      <div class="title">
        <header><i class="heading icon"></i></header>
        <h1 class="content ui header">{{entry.title}}</h1>
      </div>
      <div class="operate">
        <div class="ui small inverted buttons">
          <div class="ui red inverted button"><i class="edit icon"></i>编辑</div>
          <div class="or"></div>
          <div class="ui yellow inverted button"><i class="eraser icon"></i>删除</div>
          <div class="or"></div>
          <div class="ui pink inverted button"><i class="pencil alternate icon"></i>创建</div>
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
        <div class="content">{{ entry.description }}</div>
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
    }
  }
</script>

<style scoped lang="scss">
i.quote {
  position: relative;
  font-weight: 300;
  font-size: 10px;
  top: -14px;
}
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
        padding-bottom: 5px;
        line-height: 27px;
      }
      h1:hover {
        border-bottom: 3px solid #316ea2;
      }

    }
    .alias {
      display: flex;
      font-size: 14px;
      i {
        font-size: x-large;
      }
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
      i {
        font-size: x-large;
      }
      > span {
        margin-left: 5px;
      }
    }
    .description {
      display: flex;
      flex-direction: column;
      i {
        font-size: x-large;
      }
      .content {
        padding: 5px 20px;
      }
    }
    .currentContent {
      display: flex;
      flex-direction: column;
      i {
        font-size: x-large;
      }
    }

  }
</style>
