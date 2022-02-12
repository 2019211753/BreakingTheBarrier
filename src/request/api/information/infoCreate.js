/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const infoCreate = {
  createEntry(data) {
    return instance
      .post("/infoEntry/create", data)
  }
}

export default infoCreate;
