<template></template>

<script>
import axios from "axios";
export default {
  name: "commentFiles",
  data() {
    return { loading: true };
  },
  created() {
    var that = this;
    axios
      .get("/visit/" + sessionStorage.getItem("id"))
      .then(function (response) {
        console.log(response.data);
        that.loading = false;
      })
      .catch(function (error) {
        console.log(error);
      });
  },
};
</script>

<style scoped>
</style>
