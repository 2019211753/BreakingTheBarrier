<template>
  <div class="column">
    <a href="javascript:;">
      <img :src="imgURL" />
      <div><strong>{{fileName}}</strong></div>
    </a>
  </div>
</template>

<script>
  export default {
    name: "FileDisplayCItem",
    props: {
      // imgURL: {
      //   type: String,
      //   // default: "../../../../static/img/fileCategory/xlsx.png",
      //   // default: require(`('@/assets/img/fileCategory/'xlsx.png`))
      //   default: '~img/fileCategory/unknown.png'
      // },
      fileName: String,
      fileId: String //下一步把ID分配到下载链接中
    },
    data() {
      return {
        imgURL: require(`@/assets/img/fileCategory/unknown.png`)
      }
    },
    created() {
    },
    mounted() {
      this.getFileJPG()
    },
    updated() {
      this.getFileJPG()
    },
    methods: {
      getFileJPG() {
        this.$nextTick(function() {
          // console.log('--'+this.fileName);
          let extension = this.fileName.substring(this.fileName.lastIndexOf(".") + 1)
          // console.log(this.fileName + '---' + extension);
          switch (extension) {
            case 'png' :
              this.imgURL = require('@/assets/img/fileCategory/' + 'png.png')
              break
            case 'jpg' :
              this.imgURL = require('@/assets/img/fileCategory/' + 'jpg.png')
              break
            case 'jpeg' :
              this.imgURL = require('@/assets/img/fileCategory/' + 'jpg.png')
              break
            case 'xls' :
              this.imgURL = require('@/assets/img/fileCategory/' + 'xlsx.png')
              break
            case 'xlsx' :
              this.imgURL = require('@/assets/img/fileCategory/' + 'xlsx.png')
              break
            case 'pdf' :
              this.imgURL = require('@/assets/img/fileCategory/' + 'pdf.png')
              break
            case 'rar' :
              this.imgURL = require('@/assets/img/fileCategory/' + 'rar.png')
              break
            case 'txt' :
              this.imgURL = require('@/assets/img/fileCategory/' + 'txt.png')
              break
            case 'docx' :
              this.imgURL = require('@/assets/img/fileCategory/' + 'docx.png')
              break
            case 'zip' :
              this.imgURL = require('@/assets/img/fileCategory/' + 'zip.png')
              break
            default :
              this.imgURL = require('@/assets/img/fileCategory/' + 'unknown.png')
          }
        });
      }
    }
  }
</script>

<style scoped>
  a > div {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  a > img{
    width: 70px;
    margin: 20px auto
  }
  div.column {
    text-align: center;
  }
</style>
