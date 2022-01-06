<template>
  <div>
    <div style="width: 28%; float: left">
      <div class="ui basic segment">
        <div class="ui basic segment">
          <h3>
            组队招募<button
              class="ui mini button"
              style="margin-left: 72px; background-color: white"
              @click="more()"
            >
              更多
            </button>
          </h3>
          <div class="ui green basic segment">
            <h5 style="text-align: center">纸短情长啊，诉不完当时年少</h5>
          </div>
          <img
            class="ui medium rounded image"
            src="../../../../assets/team.jpg"
          />
        </div>
      </div>
    </div>
    <div style="width: 2%; height: 1px; float: left"></div>
    <div style="width: 70%; float: left">
      <div class="ui basic segment">
        <div class="ui segment">
          <a class="ui red ribbon label">大学生创新创业</a>

          <el-descriptions style="margin-top: 10px">
            <el-descriptions-item label="发布者"
              >kooriookami</el-descriptions-item
            >
            <el-descriptions-item label="手机号"
              >18977501507</el-descriptions-item
            >
            <el-descriptions-item label="备注">
              <el-tag size="small">沙河</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="需求"
              >已有成型的产品想法，需要坚持不懈的小伙伴，有编程经验优先</el-descriptions-item
            >
          </el-descriptions>
        </div>
        <div class="ui segment">
          <a class="ui orange right ribbon label">美国大学生数学建模</a>
          <el-descriptions style="margin-top: 10px">
            <el-descriptions-item label="用户名"
              >薛定谔想不出方程</el-descriptions-item
            >
            <el-descriptions-item label="手机号"
              >19801175389</el-descriptions-item
            >
            <el-descriptions-item label="备注">
              <el-tag size="small">本部</el-tag
              ><el-tag size="small">沙河</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="需求">求大佬带带</el-descriptions-item>
          </el-descriptions>
        </div>
        <div class="ui segment">
          <a class="ui red ribbon label">蓝俱杯</a>
          <el-descriptions style="margin-top: 10px">
            <el-descriptions-item label="用户名"
              >小兰今天吃什么</el-descriptions-item
            >
            <el-descriptions-item label="手机号"
              >18198755576</el-descriptions-item
            >
            <el-descriptions-item label="备注">
              <el-tag size="small">北京邮电大学</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="希望"
              >团队合作能力强，及时沟通不要不说话，能力第二态度第一，不能摆烂</el-descriptions-item
            >
          </el-descriptions>
        </div>
        <div class="ui segment">
          <a class="ui orange right ribbon label">唱邮心声帮帮唱</a>
          <el-descriptions style="margin-top: 10px">
            <el-descriptions-item label="用户名">kiki</el-descriptions-item>
            <el-descriptions-item label="QQ号">675797754</el-descriptions-item>
            <el-descriptions-item label="备注">
              <el-tag size="small">沙河</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="希望"
              >有没有唱林俊杰的歌的小伙伴</el-descriptions-item
            >
          </el-descriptions>
        </div>
        <div class="ui segment">
          <a class="ui red ribbon label">ICPC</a>
          <el-descriptions style="margin-top: 10px">
            <el-descriptions-item label="用户名"
              >猫猫麻完了</el-descriptions-item
            >
            <el-descriptions-item label="手机号"
              >18100876095</el-descriptions-item
            >
            <el-descriptions-item label="备注">
              <el-tag size="small">北京邮电大学</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="希望"
              >leetcodetop100做完</el-descriptions-item
            >
            <el-descriptions-item label="语言"
              >c++、python都可</el-descriptions-item
            >
          </el-descriptions>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "team",
  methods: {
    more() {
      this.$router.push("/helloWorld/team");
    },
  },
};
</script>

<style scoped>
</style>
