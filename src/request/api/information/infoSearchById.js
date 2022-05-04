/**
 * article模块接口列表
 */

import instance from "../../http";


const infoSearchById = {
  infoSearchById(query) {
    return instance
      .get(`/infoEntry/search/${query}`)
  }
}

export default infoSearchById;
