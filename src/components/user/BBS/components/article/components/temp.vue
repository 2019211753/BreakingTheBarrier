<template>
  <div>
    <a class="ui teal basic label" v-if="item.origin == true"
    >原创</a
    >
    <a class="ui blue basic label" v-else>转载</a>
  </div>
</template>

<script>
export default {
  name: "temp"
}
</script>

<style scoped>

</style>
