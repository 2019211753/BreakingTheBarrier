<template>
  <div class="info-box">
    <entry-display-item class="info-item"
      v-for="(item, index) in content"
      :key="index" :contentItem="item">
    </entry-display-item>
  </div>
</template>

<script>
  import EntryDisplayItem from "./EntryDisplayItem";
  import btbDescription from "../../../user/home/components/btbDescription";
  import btbDescriptionItem from "../../../user/home/components/btbDescriptionItem";
  export default {
    name: "EntryDisplay",
    props: {
      content: Array
    },
    components: {
      EntryDisplayItem
    }
  }
</script>

<style scoped>

  .info-item {
    width: 215px;
    height: 200px;
    /*overflow: hidden;*/
    border: 1px solid rgba(.5,.5,.5,.2);
    box-sizing: border-box;
    padding: 5px;
    text-overflow: ellipsis;
    overflow: hidden;
    /*white-space: nowrap;*/
  }
</style>
