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

@media screen and (max-width: 570px) {
  .btb-h3 {
    font-size: 16px;
  }
}
@media screen and (max-width: 375px){
  .btb-h3 {
    font-size: 14px;
  }
}
</style>
