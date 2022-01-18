/**
 * article模块接口列表
 */

import base from "../../base"; // 导入接口域名列表
import axios from "axios"; // 导入http中创建的axios实例


const adminUser = {
    serchUser(data) {
        return axios
            .post(base.base1 + "/admin/searchBlogs", { nickname: data })
    },
    controlSpeak(data) {
        return axios
            .get(base.base1 + "/admin/controlSpeak/" + data)
    }
}

export default adminUser;