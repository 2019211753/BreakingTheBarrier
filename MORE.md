### 一些没能用上的组件及用法

```javascript
 import eventBus from "../eventBus"; 
 import jwtDecode from "jwt-decode"; 
 var decode = jwtDecode(e.newValue);
 console.log(decode);
 eventBus.$emit("pushMsg", );
 window.addEventListener("setItem", (e) => {
      if (e.key == "avatar") {
        console.log(e.newValue);
        that.imgSrc = e.newValue;
      }
      var decode = jwtDecode(e.newValue);
      console.log(decode);
    });
```

```html
<div class="ui segment" v-for="(item, index) in contentList">
      <el-container>
        <el-aside width="45px"
          ><img
            class="ui medium circular image"
            style="width: 45px"
            :src="'data:image/jpg;base64,' + item.avatar"
            alt=""
        /></el-aside>
        <el-main
          >
          <h4 style="margin-top: -13px">{{ item.nickname }}</h4>
          <p style="margin-top: -13px; color: grey; font-size: 13px">
            {{ item.createTime }}
          </p>
        </el-main>
        <div
          v-if="item.solved == true"
          class="ui green top right attached label"
        >
          已解决
        </div>
        <div
          v-if="item.solved == false"
          class="ui red top right attached label"
        >
          未解决
        </div>
      </el-container>
      <el-row :gutter="24" style="margin-top: -10px">
        <el-col :span="24"
          ><h3 style="text-align: center">
            <a href="">
              <router-link
                :to="{
                  path: '/BreakingTheBarrier/BBS/articleContent',
                  query: {
                    articleId: item.id,
                    posterUserId0: item.posterUserId0,
                  },
                }"
                >{{ item.title }}
              </router-link>
            </a>
          </h3></el-col
        >
      </el-row>
      <el-row :gutter="24" style="margin-top: 5px">

        <el-col :span="24"
          ><h4 style="color: grey; text-align: center">
            {{ item.description }}
          </h4>
        </el-col> </el-row
      ><el-row :gutter="24" style="margin-top: 5px">
        <el-col :span="24">
          <h4 style="color: grey; text-align: center">
            <a class="ui teal label" v-for="tags in item.tags">
              {{ tags.name }}
            </a>
          </h4>
        </el-col>
      </el-row>
      <div class="ui divider"></div>
      <el-row :gutter="24">
        <el-col :span="6">
          <div class="ui mini button" style="background-color: white">
            <i class="thumbs up icon"></i>{{ item.likesNum }}
          </div></el-col
        >
        <el-col :span="6">
          <div class="ui mini button" style="background-color: white">
            <i class="comment icon"></i>{{ item.commentsNum }}
          </div></el-col
        >
        <el-col :span="6">
          <div class="ui mini button" style="background-color: white">
            <i class="share icon"></i>0
          </div></el-col
        >
        <el-col :span="6">
          <div class="ui mini button" style="background-color: white">
            <i class="eye icon"></i>{{ item.view }}
          </div></el-col
        >
      </el-row>
    </div> -->
    <!-- <el-skeleton :loading="loading" animated :count="7">
      <template slot="template"
        ><div class="ui segment">
          <el-container>
            <el-aside width="45px"
              ><el-skeleton-item
                variant="circle image"
                style="width: 45px; height: 45px; margin-top: 15px"
              />
            </el-aside>
            <el-main
              ><el-skeleton-item variant="text" />
              <el-skeleton-item variant="text" />
            </el-main>
          </el-container>
          <el-row :gutter="20">
            <el-col :span="20"><el-skeleton-item variant="text" /></el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="8"
              ><el-skeleton-item variant="image" style="height: 100px" />
            </el-col>
            <el-col :span="16"
              ><el-skeleton-item variant="text" /><el-skeleton-item
                variant="text" /><el-skeleton-item
                variant="text" /><el-skeleton-item
                variant="text" /><el-skeleton-item variant="text"
            /></el-col> </el-row
          ><el-row :gutter="20" style="margin-top: 5px">
            <el-col :span="20"> <el-skeleton-item variant="text" /></el-col>
          </el-row>
          <div class="ui divider"></div>
          <el-row :gutter="20">
            <el-col :span="5">
              <el-skeleton-item variant="text" style="width: 40px"
            /></el-col>
            <el-col :span="5">
              <el-skeleton-item variant="text" style="width: 40px"
            /></el-col>
            <el-col :span="5">
              <el-skeleton-item variant="text" style="width: 40px"
            /></el-col>
            <el-col :span="5"
              ><el-skeleton-item variant="text" style="width: 40px"
            /></el-col>
          </el-row>
        </div>
      </template>
    </el-skeleton>
```

```html
<el-skeleton :loading="loading" animated :count="3">
      <template slot="template"
        ><div class="ui segment">
          <el-container>
            <el-aside width="45px"
              ><el-skeleton-item
                variant="circle image"
                style="width: 45px; height: 45px; margin-top: 15px"
              />
            </el-aside>
            <el-main
              ><el-skeleton-item variant="text" />
              <el-skeleton-item variant="text" />
            </el-main>
          </el-container>
          <el-row :gutter="20">
            <el-col :span="20"><el-skeleton-item variant="text" /></el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="8"
              ><el-skeleton-item variant="image" style="height: 100px" />
            </el-col>
            <el-col :span="16"
              ><el-skeleton-item variant="text" /><el-skeleton-item
                variant="text" /><el-skeleton-item
                variant="text" /><el-skeleton-item
                variant="text" /><el-skeleton-item variant="text"
            /></el-col> </el-row
          ><el-row :gutter="20" style="margin-top: 5px">
            <el-col :span="20"> <el-skeleton-item variant="text" /></el-col>
          </el-row>
          <div class="ui divider"></div>
          <el-row :gutter="20">
            <el-col :span="5">
              <el-skeleton-item variant="text" style="width: 40px"
            /></el-col>
            <el-col :span="5">
              <el-skeleton-item variant="text" style="width: 40px"
            /></el-col>
            <el-col :span="5">
              <el-skeleton-item variant="text" style="width: 40px"
            /></el-col>
            <el-col :span="5"></el-col>
          </el-row>
        </div>
      </template>
    </el-skeleton>
```

```html
<el-skeleton :loading="loading" animated v-if="loading == true">
          <template slot="template">
            <el-row :gutter="24">
              <el-col :span="6">
                <el-skeleton-item
                  style="
                    width: 130px;
                    height: 130px;
                    margin-left: 20px;
                    margin-top: 10px;
                  "
                  variant="circle image"
                /> </el-col
              ><el-col :span="1" style="height: 1px"> </el-col>
              <el-col :span="17">
                <el-skeleton-item
                  style="height: 30px; margin-top: 20px"
                  variant="text"
                />
                <el-skeleton-item variant="text" style="margin-top: 5px" />
                <el-skeleton-item
                  style="height: 20px; margin-top: 5px"
                  variant="text"
                />
                <el-skeleton-item style="height: 20px" variant="text" />
              </el-col>
            </el-row>
          </template>
        </el-skeleton>
```

```html
<el-skeleton :loading="loading" animated v-if="loading == true">
          <template slot="template">
            <el-row :gutter="24">
              <el-col :span="6">
                <el-skeleton-item
                  style="
                    width: 130px;
                    height: 130px;
                    margin-left: 20px;
                    margin-top: 10px;
                  "
                  variant="circle image"
                /> </el-col
              ><el-col :span="1" style="height: 1px"> </el-col>
              <el-col :span="17">
                <el-skeleton-item
                  style="height: 30px; margin-top: 20px"
                  variant="text"
                />
                <el-skeleton-item variant="text" style="margin-top: 5px" />
                <el-skeleton-item
                  style="height: 20px; margin-top: 5px"
                  variant="text"
                />
                <el-skeleton-item style="height: 20px" variant="text" />
              </el-col>
            </el-row>
          </template>
        </el-skeleton>
```

