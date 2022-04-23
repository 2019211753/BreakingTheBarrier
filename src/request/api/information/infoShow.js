/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const infoShow = {
  infoShow(data) {
    return instance
      .get("/infoEntry/show?pageIndex=" + data)
  }
}

export default infoShow;
