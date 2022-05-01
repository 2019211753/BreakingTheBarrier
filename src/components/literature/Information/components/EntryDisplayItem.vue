<template>
  <div style="text-align: center;margin: 20px 0;min-width: 200px" @click="itemClick">
<!--    <span>{{contentItem}}</span>-->
    <a href="javascript:;">
      <h2 >{{contentItem.title}}</h2>
      <span>{{contentItem.currentContent}}</span>
      <i style="display: none">{{contentItem.id}}</i>
    </a>
  </div>
</template>

<script>
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
        // this.showUpdateFlag = !this.showUpdateFlag
        // document.getElementById('link').click()
      },
      linkClick() {
        document.getElementById('link').click()
      }
    },
    components: {
      // UpdateEntry
    }
  }
</script>

<style scoped>

</style>
