/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const dataDown = {
  downFile(fileId) {
    return instance
      .get("/files/download?fileId=" + fileId, {responseType: 'blob'})
  }
}

export default dataDown;
