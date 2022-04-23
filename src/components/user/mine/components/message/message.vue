<template>
  <div>
    <div class="ui segment">
      <el-row>
        <el-col :span="24">
          <div class="header">
            <div class="nav">
              <ul>
                <li>
                  <a href="">
                    <router-link to="/BreakingTheBarrier/mine/message/unread">
                      <i class="archive icon"></i> 未读
                    </router-link>
                  </a>
                </li>
                <li>
                  <a href="">
                    <router-link to="/BreakingTheBarrier/mine/message/read">
                      <i class="archive icon"></i> 已读
                    </router-link>
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: "message"
};
</script>

<style scoped>
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
