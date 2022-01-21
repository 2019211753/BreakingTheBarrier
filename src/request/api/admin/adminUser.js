/**
 * article模块接口列表
 */

import base from "../../base"; // 导入接口域名列表
import instance from "../../http";


const adminUser = {
    serchUser(data) {
        return instance
            .post("/admin/searchUser", data)
    },
    controlSpeak(data) {
        return instance
            .get("/admin/controlSpeak/" + data)
    }
}

export default adminUser;
