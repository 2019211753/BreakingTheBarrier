/**
 * article模块接口列表
 */

import base from "../../base";
import axios from "axios";


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
        return axios
            .get(base.base1 + "/customer/personal")
    }
}

export default personalInformation;
