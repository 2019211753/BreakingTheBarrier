

import axios from "axios";


const CORP_ID = 'wwf1ea95a5532cbd56';
const CORP_SECRET = 'FtzwPHl1uaxPMnGiLdywZ0fk5pPaPwY9eP92DmLF9uU';
const AGENT_ID = 1000003;
const adminTop = {
  getWXToken() {
    return axios.get('https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=' + CORP_ID + '&corpsecret=' + CORP_SECRET)
  }
};

export default adminTop;
