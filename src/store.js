import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";

Vue.use(Vuex);
const CORP_ID = 'wwf1ea95a5532cbd56';
const CORP_SECRET = 'FtzwPHl1uaxPMnGiLdywZ0fk5pPaPwY9eP92DmLF9uU';
const AGENT_ID = 1000003;
export default new Vuex.Store({
  state: {
    wxToken: "",
    text: "",
    articleId: "",
    blogId: "",
    me: {
      /*token: "",*/
      id: "",
      /* admin: "", */
      canSpeak: "",
      avatar: "",
      payCode: "",
      nickname: "",
      academy: "",
      email: "",
      major: "",
      sex: "",
      qqId: "",
      wechatId: "",
      personalSignature: "",
      donation: "",
      followedUserNum: "",
      followingUserNum: "",
      academies: []
    },
    others: {
      nickname: "",
      avatar: "",
      personalSignature: "",
      questionsNum: "",
      blogsNum: "",
      commentsNum: "",
      followingUserNum: "",
      followedUserNum: ""
    },
  },
  mutations: {
    getWXToken(state) {
      axios.get('/top/cgi-bin/gettoken?corpid=' + CORP_ID + '&corpsecret=' + CORP_SECRET).then(function (response) {
        console.log(response.data);
        state.wxToken = response.data.access_token;
        console.log(state.wxToken);
      }).catch(function (error) {
        console.log(error);
      });
    },
    sendWXMessage(state) {
      var data = {
        "touser": '@all',
        "msgtype": "template_card",
        "template_card": {
          "card_type": "news_notice",
          "source": {
            "icon_url": "https://wework.qpic.cn/wwpic/252813_jOfDHtcISzuodLa_1629280209/0",
            "desc": "好友新文章提醒",
            "desc_color": 0
          },
          "main_title": {
            "title": "纸短情长",
            "desc": "东无神话 2022-12-10"
          },
          "card_image": {
            "url": "https://wework.qpic.cn/wwpic/354393_4zpkKXd7SrGMvfg_1629280616/0",
            "aspect_ratio": 2.25
          }, "card_action": {
            "type": 1,
            "url": "https://work.weixin.qq.com/?from=openApi",
            "appid": "APPID",
            "pagepath": "PAGEPATH"
          }
        },
        "agentid": AGENT_ID,
        "safe": 0
      };
      var url='/top/cgi-bin/message/send?access_token='+state.wxToken
      console.log(url);
      axios.post(url,data=data).then(function (response) {
        console.log(response.data);
      }).catch(function (error) {
        console.log(error);
      });
    },
    getText(state, text) {
      state.text = text;
    },
    getArticleId(state, articleId) {
      state.articleId = articleId;
    },
    getBlogId(state, blogId) {
      state.blogId = blogId;
    },
    /*getToken(state, token) {
                state.me.token = token;
                console.log(state.me.token)
            },*/
    /* getCanSpeak(state, CanSpeak) {
                state.me.avatar = avatar;
            }, */
    getMyAvatar(state, avatar) {
      state.me.avatar = avatar;
    },
    getMyPayCode(state, payCode) {
      state.me.payCode = payCode;
    },
    getMyNickname(state, nickname) {
      state.me.nickname = nickname;
    },
    getMyFollowingNum(state, followingNum) {
      state.me.followingUserNum = followingNum;
    },
    getOtherFollowedNum(state, followedNum) {
      state.others.followedUserNum = followedNum;
    },
    getMySex(state, sex) {
      state.me.sex = sex;
    },
    getMyEmail(state, email) {
      state.me.email = email;
    },
    getMyQQ(state, qqId) {
      state.me.qqId = qqId;
    },
    getMyAcademy(state, academy) {
      state.me.academy = academy;
    },
    getMyMajor(state, major) {
      state.me.major = major;
    },
    getMyPersonalSignature(state, personalSignature) {
      state.me.personalSignature = personalSignature;
    },
    getMyFile1(state, data) {
      state.me.nickname = data.nickname;
      state.me.sex = data.sex;
      state.me.email = data.email;
      state.me.qqId = data.qqId;
      state.me.academy = data.academy;
      state.me.major = data.major;
      state.me.personalSignature = data.personalSignature;
    },
    getMyFile2(state, data) {
      state.me.id = data.user.id;
      state.me.canSpeak = data.user.canSpeak;
      state.me.admin = data.user.admin;
      state.me.academies = data.ACADEMIES;
      state.me.donation = data.user.donation;
      state.me.followedUserNum = data.user.followedUserNum;
      state.me.followingUserNum = data.user.followingUserNum;
    },
    getOthersFile(state, data) {
      state.others.nickname = data.userInfo.nickname;
      state.others.avatar = data.userInfo.avatar;
      state.others.personalSignature = data.userInfo.personalSignature;
      state.others.questionsNum = data.questions.length;
      state.others.blogsNum = data.blogs.length;
      state.others.commentsNum = data.comments.length;
      state.others.followingUserNum = data.userInfo.followingUserNum;
      state.others.followedUserNum = data.userInfo.followedUserNum;
    },
    turnRead(state) {
      state.readFlag = true;
    }, //效果相反了，我也不知道为啥
    turnUnread(state) {
      state.readFlag = false;
    }
  },
  actions: {}
});
