<template>
  <div class="container" v-loading="downloading">
    <div class="box">
      <div class="imgBx">
        <img v-if="true" :src="coverList[coverIndex]" alt="cover">
        <img v-else src="" alt="">
      </div>
      <div class="content">
        <div><i class="file icon"></i>文件名:</div>
        <h3>{{ fileInfo.originName }}</h3>
        <p><span><i class="circle icon"></i>文件大小:&nbsp</span>{{fileInfo.size}}</p>
        <p><span><i class="download icon"></i>下载次数:&nbsp</span>{{fileInfo.downloadCount}}</p>
        <p><span><i class="user icon"></i>上传者:&nbsp</span>{{fileInfo.uploadUser.nickname}}</p>
        <div>
          <button class="ui right labeled icon button" @click="downloadFile(fileInfo.id)">
          <i class="download icon"></i>
          下载
        </button></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "FilesCover",
  props: {
    fileInfo: Object,
    coverIndex: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      coverList: [],
      downloading: false
    }
  },
  created() {
    console.log(this.coverIndex);
    this.coverList = []
    for(let i = 1;i < 24;i++) {
      let cover = require("../../../assets/img/cover/cover (" + `${i}).jpg`)
      this.coverList.push(cover)
    }
  },
  methods: {
    downloadFile(fileId) {
      this.downloading = true
      this.$api.dataDown.downFile(fileId)
      .then(res => {
        console.log(res.data);
        const data = res.data
        const url = window.URL.createObjectURL(new Blob([data]))
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.setAttribute('download', this.fileInfo.originName)
        document.body.appendChild(link)
        link.click()
        document.body.removeChild(link)
        this.downloading = false
      })
    },
  },
}
</script>

<style scoped lang="scss">
body {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: grey;
  width: 100vw;
}
.container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  .box {
    position: relative;
    height: 250px;
    background-color: #000;
    box-shadow: 0 30px 30px rgba(0,0,0,.5);
    width: 100%;
    .imgBx {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      img
      {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        object-fit: cover;
        transition: 0.5s;
      }
    }
    .content {
      position: absolute;
      bottom: 20px;
      left: 10%;
      width: 80%;
      height: 60px;
      background: #fff;
      transition: 0.5s;
      overflow: hidden;
      //padding: 20px;
      padding: 5px 10px;
      box-sizing: border-box;
      h3 {
        font-size: 16px;
        margin: 0;
        padding: 0;
      }
      p {
        margin: 10px 0 0;
        padding: 0;
        opacity: 0;
        line-height: 1.2em;
        transition: 0.5s;
        text-align: justify;
      }
      > div {
        white-space: nowrap;
        margin-top: 10px;
      }
    }
  }
  .box:hover {
    .imgBx img {
      opacity: 0;
    }
    .content {
      width: 100%;
      height: 100%;
      bottom: 0;
      left: 0;
      p {
        opacity: 1;
        transition-delay: 0.5s;
      }
    }
  }
}
</style>
