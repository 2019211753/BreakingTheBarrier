/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const infoApprove = {
  entryApprove(data) {
    return instance
      .post("/infoEntry/" + data + "/approve")
  }
}

export default infoApprove;
