/**
 * article模块接口列表
 */

import base from "../../base";
import axios from "axios";


const adminTag = {
    addTag() {},
    deleteTag() {},
    serchBlogs(searchContent) {
        return axios
            .post("/admin/searchBlogs", {
                query: searchContent,
            })
    }

}

export default adminTag;