<template>
  <div class="ui secondary pointing menu"
       style="width: 668px;
                   margin: 0 auto">
    <slot name="titleH3">
      <h3 style="letter-spacing: 5px">资料文献</h3>
    </slot>
    <slot name="space">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</slot>
    <slot name="item">
      <a class="item active">电子书</a>
      <a class="item">考试真题</a>
      <a class="item">学院课程</a>
    </slot>
    <slot name="space">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</slot>
    <slot name="search"></slot>
<!--    <search-file></search-file>-->
  </div>
</template>

<script>
  import SearchFile from "./components/SearchFile";
  export default {
    name: "SecMenu",
    components: {
      SearchFile
    }
  }
</script>

<style scoped>

</style>
