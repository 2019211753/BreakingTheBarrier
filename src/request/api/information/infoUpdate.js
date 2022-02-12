/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const infoUpdate = {
  entryUpdate(data) {
    return instance
      .post("/infoEntry/" + data.id + "/update", {"title": data.title, newContent: data.newContent})
  }
}

export default infoUpdate;
