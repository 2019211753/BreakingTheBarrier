/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const infoShow = {
  infoShow(pageIndex) {
    return instance
      .get("/infoEntry/show?pageIndex=" + pageIndex)
  }
}

export default infoShow;
