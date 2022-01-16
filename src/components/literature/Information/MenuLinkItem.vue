<template>
  <a :class="itemClass" style="color: #00fad1" @click="pushRoute">
    <slot>LinkText</slot>
  </a>
</template>

<script>
  export default {
    name: "MenuLinkItem",
    props: {
      path: String
    },
    computed: {
      itemClass() {
        let isActiveRoute
          if(this.$route.path.indexOf(this.path) !== -1)
            isActiveRoute = true
          else
            isActiveRoute = false
        // console.log(isActiveRoute);
        if(isActiveRoute)
          return 'item active'
        else
          return 'item'
      }
    },
    methods: {
      pushRoute() {
        this.$router.push(this.path)
      }
    }
  }
</script>

<style scoped>

</style>
