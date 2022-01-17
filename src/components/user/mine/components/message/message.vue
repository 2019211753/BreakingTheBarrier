<template>
  <div>
    <div class="ui segment">
      <el-row>
        <el-col :span="24"
          ><div class="header">
            <div class="nav">
              <ul>
                <li>
                  <a href="" @click="turnUnread()">
                    <router-link to="/helloWorld/mine/message/unread">
                      <i class="archive icon"></i> 未读
                    </router-link>
                  </a>
                </li>
                <li>
                  <a href="" @click="turnRead()">
                    <router-link to="/helloWorld/mine/message/read">
                      <i class="archive icon"></i> 已读
                    </router-link>
                  </a>
                </li>
                <li v-if="$store.state.readFlag == true">
                  <a href="" @click="readAll()">
                    <i class="archive icon"></i> 全部标记为已读
                  </a>
                </li>
              </ul>
            </div>
          </div></el-col
        >
      </el-row>
    </div>
    <div><router-view></router-view></div>
  </div>
</template>

<script>
/* axios.defaults.headers["token"] = sessionStorage.getItem("token"); */
import axios from "axios";
export default {
  name: "message",
  data() {
    return { flag: true };
  },
  methods: {
    turnRead() {
      var that = this;
      this.$store.commit("turnUnread");
    },
    turnUnread() {
      var that = this;
      this.$store.commit("turnRead");
    },
    readAll() {
      var p1 = new Promise((resolve, reject) => {
        axios
          .get("/customer/messages/readAllComments")
          .then((res) => {
            resolve(res);
          })
          .catch((err) => {
            reject(err);
          });
      });

      var p2 = new Promise((resolve, reject) => {
        axios
          .get("/customer/messages/readAllLikes")
          .then((res) => {
            resolve(res);
          })
          .catch((err) => {
            reject(err);
          });
      });

      // 调用Promise.all().then(res=>{})
      Promise.all([p1, p2]).then((res) => {
        console.log(res);
      });
    },
  },
};
</script>

<style scoped>
.ui.feed {
  margin-top: 20px;
}
.ui.icon.button {
  background-color: white;
}

.nav ul li {
  float: left;
  margin: -20px 15px;
}

.nav ul li a {
  display: block;
  height: 42px;
  padding: 0 1px;
  line-height: 42px;
  font-size: 16px;
  color: black;
  font-weight: 600;
}

.nav ul a:hover {
  border-bottom: 4px solid orange;
  /*   color: black; */
}

a {
  text-decoration: none;
}

.header {
  width: 100%;
  height: 30px;
  /* background-color: rgb(100, 133, 208); */
  float: left;
}

li {
  list-style: none;
}
</style>
