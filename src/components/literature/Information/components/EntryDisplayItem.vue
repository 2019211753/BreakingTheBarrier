<template>
  <div style="text-align: center;margin: 20px 0;min-width: 200px">
    <a href="javascript:;">
      <h2 @click="itemClick">{{contentItem.title}}</h2>
      <span>{{contentItem.currentContent}}</span>
      <i style="display: none">{{contentItem.id}}</i>
    </a>
    <update-entry :id="contentItem.id" :show-update-flag="showUpdateFlag"></update-entry>
  </div>
</template>

<script>
  import UpdateEntry from "./UpdateEntry";
  export default {
    name: "EntryDisplayItem",
    data() {
      return {
        showUpdateFlag: false
      }
    },
    props: {
      contentItem: Object
    },
    mounted() {
    },
    methods: {
      itemClick() {
        this.showUpdateFlag = !this.showUpdateFlag
      }
    },
    components: {
      UpdateEntry
    }
  }
</script>

<style scoped>

</style>
