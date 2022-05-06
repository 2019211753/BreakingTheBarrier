/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const infoDelete = {
  infoDelete(entryId) {
    return instance
      .post("/infoEntry/delete/" + entryId)
  }
}

export default infoDelete;
