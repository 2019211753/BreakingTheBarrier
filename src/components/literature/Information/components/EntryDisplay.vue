<template>
  <div class="ui container">
    <entry-display-item
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

</style>
