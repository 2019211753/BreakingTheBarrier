<template>
  <div class="ui segment" style="width: 500px;margin-left: 150px">
    <div class="ui right rail">
      <div class="ui segment">
        <h3 class="ui header">目录</h3>
      </div>
    </div>

    <h5 class="ui header">{{title}}</h5>
<!--    <button class="ui button mini">修改词条</button>-->
    <router-link
      :to="{
        path: '/BreakingTheBarrier/literature/information/EntryCreate',
        query: {
          id: this.id,
          title: this.title,
          currentContent: this.currentContent
        }
    }" class="ui button blue mini">修改词条</router-link>
    <br>
    <br>
    <div>
<!--      <p>至于计算方法，因为计算机是离散的，无法直接处理非离散的数学问题，将两者联系起来就是计算方法要研究的，简单说就是运用电子计算机处理数学-->
<!--      问题的方法。</p>-->
      {{currentContent}}
    </div>

    <h3 class="ui horizontal divider header">
      <i class="tag icon blue" style="margin-left: 0"></i>描述
    </h3>

    <div class="ui vertical segment">
      <p>1、 掌握数学物理方程的基本概念，如线性、非线性、拟线性、阶数等；了解典型二阶线性偏微分方程如弦、杆、膜、振动，电磁场、热传导、反应
        扩散，平稳状态、守恒律等方程的建立与定解条件的提法。掌握二阶线性偏微分方程的分类方法。</p>
      <p>2、 明确固有值及固有函数系的作用，熟练运用分离变量法处理椭圆型、抛物型、双曲型的齐次与非齐次方程及其第一、二、三类边界条件，了解
        Legendre多项式、Bessel函数等特殊函数在偏微分方程中的应用及Sturm-Liouville方程的固有值问题。</p>
      <p>3、掌握波动方程的D'Alembert解法；熟练掌握Fourier、Laplace等积分变换并运用于解偏微分方程。掌握调和函数的性质及Green函数法在球域、
        半空间等特殊区域上的运用。数理方程归根结底就是研究如何运用偏微分方程求解物理问题。</p>
    </div>
  </div>
</template>

<script>
  export default {
    name: "EntryItem",
    data() {
      return {
        id: '',
        title: '',
        currentContent: ''
      }
    },
    created() {
      this.id = this.$route.query.id
      this.title = this.$route.query.title
      this.currentContent = this.$route.query.currentContent
    }
  }
</script>

<style scoped>

</style>
