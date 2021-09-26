<template>
  <div>
    <el-pagination background
                   layout="prev, pager, next, total, jumper"
                   @size-chage="sizeChange"
                   @current-change="currentChange">
<!--      class="el-pagination"-->
<!--      layout="prev, pager, next"-->
<!--      @current-change="handleCurrentChange"-->
<!--      :page-count="pageSize"-->
<!--      :hide-on-single-page="true"-->
<!--    >-->
    </el-pagination>
  </div>
</template>

<script>
  export default {
    name: "Paged",
    data() {
      return {
        pageSize: '0',
      }
    },
    methods: {
      sizeChange() {
        console.log('sizeChange函数被执行');
      },
      currentChange() {
        console.log('currentChange函数被执行');
      }
    }
  }
</script>

<style scoped>

</style>
