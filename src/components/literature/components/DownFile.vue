<template>
  <div id="down">
    <button @click="downFile">下载文件</button>
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: "DownFile",
    methods: {
      downFile() {
        // axios.get('files/download',{
        //   responseType:'blob',
        //   params: {
        //     fileId: '',
        //   }
        // }).then(msg => {
        //   if (msg) {
        //     // console.log(msg.data) //打印出来是blob对象，已经不是乱码了
        //     // console.log(msg);
        //     let url = window.URL.createObjectURL(msg.data); //表示一个指定的file对象或Blob对象
        //     // console.log(url,"看一下这是啥")
        //     let a = document.createElement("a");
        //     document.body.appendChild(a);
        //     let fileName = msg.headers["content-disposition"].split(";")[1].split("=")[1];  //filename名称从后端返回的headers截取
        //     // let fileName = params.objectName // 这里也可以这样获取到文件名
        //     a.href = url;
        //     a.download = fileName; //命名下载名称
        //     a.click(); //点击触发下载
        //     window.URL.revokeObjectURL(url);  //下载完成进行释放
        //   }
        // })

        let that = this
        that.$api.dataDown
          .downFile(fileId)
          .then(msg => {
            if (msg) {
              // console.log(msg.data) //打印出来是blob对象，已经不是乱码了
              // console.log(msg);
              let url = window.URL.createObjectURL(msg.data); //表示一个指定的file对象或Blob对象
              // console.log(url,"看一下这是啥")
              let a = document.createElement("a");
              document.body.appendChild(a);
              let fileName = msg.headers["content-disposition"].split(";")[1].split("=")[1];  //filename名称从后端返回的headers截取
              // let fileName = params.objectName // 这里也可以这样获取到文件名
              a.href = url;
              a.download = fileName; //命名下载名称
              a.click(); //点击触发下载
              window.URL.revokeObjectURL(url);  //下载完成进行释放
            }
          })
          .catch(err => {
            console.log(err);
            console.log('咋回事');
          })

      }
    }
  }
</script>

<style scoped>

</style>
