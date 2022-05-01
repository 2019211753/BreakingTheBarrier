<template>
  <div
    class="itemBox"
    style="" @click="itemClick">
<!--    <span>{{contentItem}}</span>-->
    <div class="imgItem"></div>
    <div>
      <h2 >{{contentItem.title}}</h2>
      <span>{{contentItem.currentContent}}</span>
      <i style="display: none">{{contentItem.id}}</i>
    </div>
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

<style scoped lang="scss">
  .itemBox {
    text-align: center;
    margin: 20px 0;
    min-width: 200px;
  }
</style>
