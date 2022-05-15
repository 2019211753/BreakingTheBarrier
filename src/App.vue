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
@import "./assets/css/btb.scss";
.ui.container {
  /*width: 1200px;*/
}
::-webkit-scrollbar {
  display: none;
}


</style>
