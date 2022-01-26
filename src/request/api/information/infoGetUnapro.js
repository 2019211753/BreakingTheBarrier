/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const infoGetUnapro = {
  infoGetUnapro(data) {
    return instance
      .get("/infoEntry/unapprovedEntries")
  }
}

export default infoGetUnapro;
