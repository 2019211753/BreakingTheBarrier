<template>
  <div>
    <div class="leftSide">
      <el-input class="el-input" placeholder="" v-model="nickname">
        <template slot="prepend">修改昵称</template>
      </el-input>
      <el-input class="el-input" placeholder="" v-model="passWord">
        <template slot="prepend">修改密码</template>
      </el-input>
      <el-input class="el-input" placeholder="" v-model="surePassWord">
        <template slot="prepend">确认密码</template>
      </el-input>
      <el-input class="el-input" placeholder="" v-model="personalSignature">
        <template slot="prepend">个性签名</template>
      </el-input>
      <el-select class="el-select" v-model="value" placeholder="请选择学院">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option> </el-select
      ><el-select class="el-select" v-model="value" placeholder="请选择专业">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <div class="ui right floated button">确定</div>
    </div>
    <div class="rightSide">
      <br /><br /><br /><br /><br />
      <div class="framework">
        <img
          class="ui centered small circular image"
          src="../../../../../assets/avatar.jpg"
        />
        <br />
        <div class="ui fluid button" @click="uploadAvatar()">上传头像</div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "information",
  data() {
    return {
      nickname: "",
      passWord: "",
      surePassWord: "",
      personalSignature: "",
      options: [
        {
          value: "选项1",
          label: "黄金糕",
        },
        {
          value: "选项2",
          label: "双皮奶",
        },
        {
          value: "选项3",
          label: "蚵仔煎",
        },
        {
          value: "选项4",
          label: "龙须面",
        },
        {
          value: "选项5",
          label: "北京烤鸭",
        },
      ],
      value: "",
    };
  },
  methods: {uploadAvatar(){}},
};
</script>

<style scoped>
.leftSide {
  width: 60%;
  /* height: 200px; */
  /* background-color: aqua; */
  float: left;
}
.rightSide {
  width: 40%;
  height: 416px;
  /* background-color: rgb(113, 26, 170); */
  float: left;
}
.framework {
  width: 80%;
  height: 220px;
  margin: auto;
  /* background-color: aqua; */
}
.el-input {
  margin-top: 20px;
}
.el-select {
  margin-top: 20px;
  width: 100%;
}
.ui.right.floated.button {
  margin-top: 20px;
}
</style>
