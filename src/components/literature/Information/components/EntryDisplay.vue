<template>
  <div class="file-display ui three column divided grid">
    <entry-display-item class="column" style="
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;"
      v-for="(item, index) in content"
      :key="index" :contentItem="item">
    </entry-display-item>
  </div>
</template>

<script>
  import EntryDisplayItem from "./EntryDisplayItem";
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
  div.file-display {
    position: relative;
    left: 200px;
    background-color: aliceblue;
    padding: 0 40px;
    width: 505px;
    display: inline-block;
  }
</style>
