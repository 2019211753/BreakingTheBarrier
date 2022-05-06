<template>
  <!--文件展示区-->
  <file-display @handlePage="handlePage">
    <file-display-c-item
      slot="firstRow"
      v-for="(item, index) in files.content"
      :mySrc="files.mySrc"
      :file-name="item.originName"
      :file-id="item.id"
      :key="index">
    </file-display-c-item>
  </file-display>
  *<!--搜索文件-->
  <search-file @search="search"></search-file>
  *<!--分页-->
  <el-pagination
    :total="page.totalElements"
    :page-size="9"
    :current-page="page.currentPage"
    @current-change="handleCurrentChange"
    layout="total, prev, pager, next">
  </el-pagination>
  *<!--上传？-->
  <button class="ui button" @click="downFile">按钮</button>

</template>

<script>
export default {
  name: "temp"
}
</script>

<style scoped>

</style>
