<template>
  <div class="hot-file ui container">
    <slot name="titleH3"><h3>热门资料</h3></slot>
    <div>
      <slot name="item">
        <a href="javascript:;" class="">
          <div class="">
            <b style="vertical-align: top">问答系统_核心技术_发展趋势_王树西.pdf
            </b>
          </div>
          <img src="../../assets/img/fileCategory/pdf.png"/>
        </a>
      </slot>
    </div>
<!--    <div class="ui divider"></div> &lt;!&ndash;分隔线&ndash;&gt;-->
    <div>
      <slot name="item">
        <a href="javascript:;" class="" src="../../assets/img/fileCategory/pdf.png">
          <div class="">
            <b style="vertical-align: top">网络问卷调查的数据质量控制研究_余富强.pdf
            </b>
          </div>
          <img src="../../assets/img/fileCategory/pdf.png"/>
        </a>
      </slot>
    </div>
<!--    <div class="ui divider"></div> &lt;!&ndash;分隔线&ndash;&gt;-->
    <div>
      <slot name="item">
        <a href="javascript:;" class="" src="../../assets/img/fileCategory/pdf.png">
          <div class="">
            <b style="vertical-align: top">网络问卷调查的数据质量控制研究_余富强.pdf
            </b>
          </div>
          <img src="../../assets/img/fileCategory/pdf.png"/>
        </a>
      </slot>
    </div>
    <slot name="upFile">
      <div style="text-align: center">
        <button class="ui button">
          <i class="angle double down icon"
             style="width: 210px"></i>
        </button>
      </div>
      <br>
      <!--上传-->
      <up-file></up-file>
    </slot>
  </div>
</template>

<script>
  import UpFile from "./components/UpFile";
  export default {
    name: "HotFile",
    components: {
      UpFile
    }
  }
</script>

<style scoped>
  b + img {
    width: 50px;
    margin: 0 2px;
  }
  div.hot-file.ui.container  > div  div {
    display: inline-block;
    max-width: 150px;
    width: -moz-fit-content;
    width: fit-content;
    vertical-align: super;
  }
  div.hot-file img {
    width: 35px;
    margin: 0;
  }
  a > div {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
</style>
