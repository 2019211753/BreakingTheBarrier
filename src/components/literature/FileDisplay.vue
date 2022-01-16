<template>
  <div class="file-display ui three column divided grid" id="fileDisplay">
    <div class="row">
      <slot name="firstRow"></slot>
    </div>
    <div class="row">
      <slot name="secondRow"></slot>
    </div>
    <div class="row">
      <slot name="thirdRow"></slot>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import FileDisplayCItem from "./Information/FileDisplayCItem";
  export default {
    name: "FileDisplay",
    data() {
      return {
        page: {
          totalElements: 0,
          totalPages: 0,
          numberOfElements: 0
        },
      }
    },
    /*初识渲染*/
    /*10.15 注释掉这段，竟然不超时了。。。我不理解 就这样吧*/
    /*10.30 超时应该是由于查询全部文件*/

    mounted() {
      axios('files/find',{
        params: {
          query: '',
          pageIndex: 0
        }
      })
        .then(res => {
        // console.log(res);
        // console.log('重新渲染');
        let page = res.data.data.page
        this.page.totalElements = page.totalElements
        this.page.totalPages = page.totalPages
        this.page.numberOfElements = page.numberOfElements
        this.$emit('handlePage', this.page)
      })
      .catch(err => {
        alert(err)
      })
    },
    methods: {
    },
    components: {
      FileDisplayCItem
    }
  }
</script>

<style scoped>
  div.file-display {
    position: relative;
    left: 200px;
    background-color: aliceblue;
    padding: 0 40px;
    width: 505px;
    display: inline-block;
  }
  a > div {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  a > img{
    width: 70px;
    margin: 20px auto
  }
  div.row {
    width: 480px;
    margin-bottom: 10px;
  }
  div.row > a {
    text-align: center;
  }
  div.column {
    text-align: center;
  }

</style>
