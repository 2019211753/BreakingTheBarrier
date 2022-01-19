/**
 * article模块接口列表
 */

import base from "../../base";

import instance from "../../http";

const personalInformation = {
    uploadAvatar(data) {
        return instance
            .post("/customer/uploadAvatar", data)
    },
    modifyAllInformation(data) {
        return instance
            .post("/customer/modifyAll", data)
    },
    getPersonalInformation() {
        return instance
            .get("/customer/personal")
    }
}

export default personalInformation;