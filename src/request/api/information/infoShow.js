/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const infoShow = {
  infoShow() {
    return instance
      .get("/infoEntry/show")
  }
}

export default infoShow;
