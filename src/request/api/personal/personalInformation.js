/**
 * article模块接口列表
 */

import base from "../../base";
import axios from "axios";
import instance from "../../http";

const personalInformation = {
    uploadAvatar(data) {
        return axios
            .post(base.base1 + "/customer/uploadAvatar", data)
    },
    modifyAllInformation(data) {
        return axios
            .post(base.base1 + "/customer/modifyAll", data)
    },
    getPersonalInformation() {
        return instance
            .get(base.base1 + "/customer/personal")
    }
}

export default personalInformation;
