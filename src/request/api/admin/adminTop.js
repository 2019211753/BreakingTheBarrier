import axios from "axios";
import store from "../../../store";

/*const CORP_ID = 'wwf1ea95a5532cbd56';
const CORP_SECRET = 'FtzwPHl1uaxPMnGiLdywZ0fk5pPaPwY9eP92DmLF9uU';
const AGENT_ID = 1000003;*/
var that = this;
const adminTop = {
  getWXToken() {
    return axios.get('top/cgi-bin/gettoken?corpid=' + store.state.CORP_ID + '&corpsecret=' + store.state.CORP_SECRET).then(function (response) {
      store.commit("getWXToken", response.data.access_token);
    }).catch(function (error) {
      console.log(error);
    });
  },
  sendWXMessage() {
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
      "agentid":  store.state.AGENT_ID,
      "safe": 0
    };
    var url = 'top/cgi-bin/message/send?access_token=' +  store.state.wxToken
    axios.post(url, data = data).then(function (response) {
    }).catch(function (error) {
      console.log(error);
    });
  }
};
export default adminTop;
