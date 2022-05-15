/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const dataFind = {
  findFiles(query, pageIndex) {
    return instance
      .get("/files/find?query=" + query + "&pageIndex=" + pageIndex)
  }
}

export default dataFind;
