/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";

const adminTag = {
    addTag(data) {
        return instance.post("/admin/tags/input", data);
    },
    deleteTag(data) {
        return instance.get("/admin/tags/" + data + "/delete");
    }
};

export default adminTag;