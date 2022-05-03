<template>
  <div class="btb-h5">
    <i :class="`${icon} icon`"></i>
    <span>{{label}}</span><span v-if="flag">:</span>
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: "BTBDescriptionItem",
  props: {
    label: String,
    icon: {
      type: String,
      default: 'circle'
    },
    flag: {
      type: Boolean,
      default: true //默认冒号显示
    },
  },
}
</script>

<style scoped lang="scss">
  div.btb-h5 {
    //display: flex;
    //align-items: baseline;
    box-sizing: border-box;
    padding: 0 0.5vw;
    color: #606266;
    border: 1px solid rgba(.1, .1, .1, .08);
    margin: 0.5vw;
    > span {
      white-space: nowrap;
      text-overflow: ellipsis;
      //overflow: hidden;
    }
  }
</style>
