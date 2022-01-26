/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const dataUpload = {
  upFile(data) {
    return instance
      .post("/files/upload", data)
  }
}

export default dataUpload;
