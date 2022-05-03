<template>
  <btb-description>

<!--    <btb-description-item></btb-description-item>-->
  </btb-description>
</template>

<script>
import btbDescription from "../../../user/home/components/btbDescription";
import btbDescriptionItem from "../../../user/home/components/btbDescriptionItem";
  export default {
    name: "EntryDisplayItem",
    components: { btbDescription, btbDescriptionItem },
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
  }
</script>

<style scoped lang="scss">
  .itemBox {
    text-align: center;
    margin: 20px 0;
    min-width: 200px;
  }
</style>
