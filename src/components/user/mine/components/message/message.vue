<template>
  <div>
    <div class="ui segment">
      <el-row>
        <el-col :span="24"
        >
          <div class="header">
            <div class="nav">
              <ul>
                <li>
                  <a href="" @click="turnUnread()">
                    <router-link to="/BreakingTheBarrier/mine/message/unread">
                      <i class="archive icon"></i> 未读
                    </router-link>
                  </a>
                </li>
                <li>
                  <a href="" @click="turnRead()">
                    <router-link to="/BreakingTheBarrier/mine/message/read">
                      <i class="archive icon"></i> 已读
                    </router-link>
                  </a>
                </li>
                <li v-if="$store.state.readFlag == true">
                  <a @click="readAll()">
                    <i class="archive icon"></i> 全部标记为已读
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </el-col
        >
      </el-row>
    </div>
    <div>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>

export default {
  name: "message",
  data() {
    return {flag: true};
  },
  methods: {
    turnRead() {
      var that = this;
      that.$store.commit("turnUnread");
    },
    turnUnread() {
      var that = this;
      that.$store.commit("turnRead");
    },
    readAll() {
      var that = this;
      var p1 = new Promise((resolve, reject) => {
        that.$api.personalMessage.readAllComments()
          .then((res) => {
            resolve(res);
          })
          .catch((err) => {
            reject(err);
          });
      });

      var p2 = new Promise((resolve, reject) => {
        that.$api.personalMessage.readAllLikes()
          .then((res) => {
            resolve(res);
          })
          .catch((err) => {
            reject(err);
          });
      });

      // 调用Promise.all().then(res=>{})
      Promise.all([p1, p2]).then((res) => {

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
}

a {
  text-decoration: none;
}

.header {
  width: 100%;
  height: 30px;
  float: left;
}

li {
  list-style: none;
}
</style>
