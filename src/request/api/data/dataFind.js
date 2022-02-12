/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const dataFind = {
  findFiles(data) {
    return instance
      .get("/files/find?query=" + data.query + "&pageIndex=" + data.pageIndex)
  }
}

export default dataFind;
