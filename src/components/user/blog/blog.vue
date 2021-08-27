<template>

</template>

<script>import axios from "axios";
axios.defaults.headers["token"] = sessionStorage.getItem("token");
export default {
  name: "blog"
}
</script>

<style scoped>

</style>
