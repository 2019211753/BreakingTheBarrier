/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const infoApprove = {
  entryApprove(entryId) {
    return instance
      .post("/infoEntry/" + entryId + "/approve")
  }
}

export default infoApprove;
