/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const infoUpdate = {
  entryUpdate(data) {
    return instance
      .post("/infoEntry/" + data + "/update")
  }
}

export default infoUpdate;
