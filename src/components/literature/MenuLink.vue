<template>
  <div class="ui secondary pointing menu"
       style="background: #17353b;
                 color: #00ffe0; width: 290px;
                 margin: 0 auto" id="nav1st">
    <menu-link-item path="/helloWorld/literature/data">资料库</menu-link-item>
    <menu-link-item>问卷</menu-link-item>
    <menu-link-item>组队</menu-link-item>
    <menu-link-item path="/helloWorld/literature/information">信息百科</menu-link-item>
  </div>
</template>

<script>
  import MenuLinkItem from "./Information/MenuLinkItem";
  export default {
    name: "MenuSec",
    data() {
      return {
      }
    },
    computed: {
      isActive() {
        return true
      }
    },
    methods: {
    },
    components: {
      MenuLinkItem
    }
  }
</script>

<style scoped>

</style>
