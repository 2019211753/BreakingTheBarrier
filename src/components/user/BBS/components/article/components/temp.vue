<template>
  <div>
    *<!--用户昵称-->
    <div class="ui fluid input">
      <input
        type="text"
        :placeholder="$store.state.me.nickname"
        v-model="nickname"
      />
    </div>
    *<!--性别-->
    <div class="ui fluid selection dropdown" style="width: 175px">
      <input type="hidden" name="gender"/>
      <i class="dropdown icon"></i>
      <div class="default text" v-if="$store.state.me.sex">
        <div v-if="$store.state.me.sex == 'true'">男</div>
        <div v-if="$store.state.me.sex == 'false'">女</div>
      </div>
      <div class="default text" v-else>无</div>
      <div class="menu">
        <div class="item" @click="boy()">男</div>
        <div class="item" @click="girl()">女</div>
      </div>
    </div>
    *<!--修改密码-->
    <div class="ui fluid input">
      <input type="text" placeholder="长度为7至11" v-model="passWord"/>
    </div>
    *<!--确认密码-->
    <div class="ui fluid input" style="margin-left: -26px; width: 173px">
      <input
        type="text"
        placeholder="再次输入确认"
        v-model="surePassWord"
      />
    </div>
    *<!--邮箱-->
    <div class="ui fluid input">
      <input
        type="text"
        :placeholder="$store.state.me.email"
        v-model="email"
      />
    </div>
    *<!--QQ-->
    <div class="ui fluid input">
      <input
        type="text"
        style="margin-left: -3px"
        :placeholder="$store.state.me.qqId"
        v-model="QQ"
      />
    </div>
    *<!--学院-->
    <div class="ui fluid selection dropdown" style="width: 175px">
      <input type="hidden" name="gender"/>
      <i class="dropdown icon"></i>
      <div class="default text" v-if="$store.state.me.academy">
        {{ $store.state.me.academy }}
      </div>
      <div class="default text" v-else>无</div>
      <div class="menu">
        <div
          v-for="(item, index) in academyList"
          class="item"
          @click="selectAcademy(index)"
        >
          {{ index }}
        </div>
      </div>
    </div>
    *<!--专业-->
    <div class="ui fluid selection dropdown" style="width: 175px">
      <input type="hidden" name="gender"/>
      <i class="dropdown icon"></i>
      <div class="default text" v-if="$store.state.me.major">
        {{ $store.state.me.major }}
      </div>
      <div class="default text" v-else>无</div>
      <div class="menu">
        <div
          class="item"
          v-for="(item, index) in majorList"
          @click="selectMajor(item)"
        >
          {{ item }}
        </div>
      </div>
    </div>
    *<!--签名-->
    <div class="ui fluid input">
      <input
        type="text"
        :placeholder="$store.state.me.personalSignature"
        v-model="personalSignature"
      />
    </div>
    *<!--确定-->
    <div class="ui right floated teal button" @click="sure()">
      确定
    </div>
    *<!--上传收款码-->
    <el-upload
      class="avatar-uploader"
      action=""
      :before-upload="beforeAvatarUpload"
      :http-request="handleTestSuccess"
      :show-file-list="false"
    >
      <div class="ui icon teal button" @click="uploadWX()">
        <i class="ui plus icon"></i>
      </div>
    </el-upload>
  </div>
</template>

<script>
export default {
  name: "temp"
}
</script>

<style scoped>

</style>
