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
      <div class="ui right floated button" @click="sure()">确定</div>
    </div>
    <!-- "http://39.105.212.210/customer/uploadAvatar"
          :headers="headers" :on-preview="handlePictureCardPreview"
          :on-remove="handleRemove"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"-->
    <div class="rightSide">
      <br /><br /><br /><br /><br />
      <div class="framework">
        <el-upload
          class="avatar-uploader"
          action=""
          :http-request="handleTestSuccess"
          :show-file-list="false"
        >
          <img v-if="imageUrl" :src="imageUrl" class="avatar" />
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>

        <!-- <img
          class="ui centered small circular image"
          src="../../../../../assets/avatar.jpg"
        /> -->
        <br />
        <div class="ui disabled fluid button">上传头像</div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
/* var token = sessionStorage.getItem("token");
window.addEventListener("setItem", (e) => {
  console.log(e);
  token = e.newValue;
}); */
/* axios.defaults.headers["token"] = sessionStorage.getItem("token"); */
export default {
  name: "information",
  /* computed: {
    headers() {
      return {
        Authorization: sessionStorage.getItem("token"), // 直接从本地获取token就行
      };
    },
  }, */
  data() {
    return {
      imageUrl: "",
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
  methods: {
    /* handleRemove(file, fileList) {
      console.log("____________________");
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      console.log("____________________");
      console.log("file.url");
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    }, */
    /* uploadAvatar() {
      var that = this;
      axios
        .post("/customer/uploadAvatar")
        .then(function (response) {
          console.log(response.data);
        })
        .catch(function (error) {
          console.log(error);
        });
    }, */
    handleTestSuccess(file) {
      if (file.file.type.indexOf("image") == -1) {
        this.$message.error("请上传图片类型的文件");
        this.$refs.upload_img.uploadFiles =
          this.$refs.upload_img.uploadFiles.filter((item) => {
            return file.file.name != item.name;
          });
        return;
      }
      const formData = new FormData();
      formData.append("file", file.file);
      axios
        .post("/customer/uploadAvatar", formData)
        .then((res) => {
          if (res.status === 200) {
            console.log(res.data);

            this.$message.success("上传头像成功!");
          }
        })
        .catch((err) => {
          this.$refs.upload_img.uploadFiles =
            this.$refs.upload_img.uploadFiles.filter((item) => {
              return file.file.name != item.name;
            });
          this.$message.error("上传失败!");
        });
    },
    sure() {
      var that = this;
      if (that.passWord === that.surePassWord) {
        axios
          .post("/customer/modifyAll", {
            nickname: that.nickname,
            passWord: that.passWord,
            personalSignature: that.personalSignature,
          })
          .then(function (response) {
            console.log(response.data);
            that.resetSetItem("token", response.data.data.token);
          })
          .catch(function (error) {
            console.log(error);
          });
      } else {
        that.$message.error("两次输入的密码不相同");
      }
    },
  },
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
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
