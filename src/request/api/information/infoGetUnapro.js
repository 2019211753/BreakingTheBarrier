/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const infoGetUnapro = {
  infoGetUnapro(pageIndex) {
    return instance
      .get(`/infoEntry/unapprovedEntries?pageIndex=${pageIndex}`)
  }
}

export default infoGetUnapro;
