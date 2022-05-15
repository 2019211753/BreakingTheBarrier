/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const infoDisapprove = {
  entryDisapprove(entryId) {
    return instance
      .post("/infoEntry/" + entryId + "/disapprove")
  }
}

export default infoDisapprove;
