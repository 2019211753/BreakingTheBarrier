/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const dataDown = {
  downFile(data) {
    return instance
      .get("/files/download?fileId=" + data.query)
  }
}

export default dataDown;
