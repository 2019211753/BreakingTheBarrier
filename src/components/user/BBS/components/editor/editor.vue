<template>
  <v-md-editor
    v-model="text"
    left-toolbar="undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code | save | emoji"
    :disabled-menus="[]"
    @change="reformText"
    @upload-image="handleUploadImage"
    height="800px"
    style="margin-top: 25px"
  >
  </v-md-editor>
</template>

<script>
const imageConversion = require("image-conversion");
export default {
  name: "editor",
  data() {
    return {
      text: "",
    };
  },
  methods: {
    reformText(text, html) {
      var that = this;
      that.$store.commit("getText", html);

    },
    handleUploadImage(event, insertImage, file) {
      // 拿到 files 之后上传到文件服务器，然后向编辑框中插入对应的内容
      /* console.log(file);
      const isJpgOrPng =
        file.type === "image/jpeg" ||
        file.type === "image/png" ||
        file.type === "image/bmp" ||
        file.type === "image/gif" ||
        file.type === "image/jpg";
      if (!isJpgOrPng) {
        this.$message.error("上传头像图片只能是 JPG 或 PNG 格式!");
        return false;
      } */

      return new Promise((resolve) => {
        imageConversion.compressAccurately(file[0], 300).then((res) => {

          const formData = new FormData();
          formData.append("files", res);
          var that = this;
          that.$api.userArticle
            .uploadPicture(formData)
            .then((res) => {

              insertImage({
                url: res.data.data.fileUrls[0],
                desc: "image",
                width: "554px",
                height: "auto",
              });
            })
            .catch((err) => {});
        });
      });
    },
  },
};
</script>

<style scoped>
</style>
