<template>
  <div class="ui container">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>

<style>
.ui.container {
  /*width: 1200px;*/
}
::-webkit-scrollbar {
  display: none;
}
</style>
