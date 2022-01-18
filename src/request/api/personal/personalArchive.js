/**
 * article模块接口列表
 */

import base from "../../base";
import axios from "axios";


const personalArchive = {
    archiveBlog() {
        return axios
            .get(base.base1 + "/customer/archivesBlog")
    },
    archiveQuestion() {
        return axios
            .get(base.base1 + "/customer/archivesQuestion")
    },
    archiveComment() {
        return axios
            .get(base.base1 + "/customer/archiveComment")
    },
    archiveLike() {
        return axios
            .get(base.base1 + "/customer/archiveLike")
    }
}

export default personalArchive;