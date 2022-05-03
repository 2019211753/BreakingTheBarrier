<template>
  <div class="btb-posts">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: "BTBDescription",
}
</script>

<style scoped lang="scss">
.btb-posts {
  display: flex;
  flex-direction: column;
  color: #606266;
> div:nth-child(1) {
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
  justify-content: space-between;
  margin: 10px 0;
}
> div:nth-child(2) {
  margin: 10px 0;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
}
}
</style>
