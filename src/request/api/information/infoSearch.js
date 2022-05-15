/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const infoSearch = {
  infoSearch(query) {
    return instance
      .get(`/infoEntry/search?pageIndex=0&query=${query}`)
  }
}

export default infoSearch;
