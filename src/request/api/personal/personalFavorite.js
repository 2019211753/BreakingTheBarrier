/**
 * article模块接口列表
 */

import base from "../../base";
import axios from "axios";


const personalFavorite = {
    createFavorite(data) {
        return axios
            .post(base.base1 + "/customer/favorite/create", data)
    },
    deleteFavorite(data) {
        return axios
            .get(base.base1 + "/customer/favorite/" + data + "/delete")
    },
    editFavorite(data) {
        return axios
            .post(base.base1 + "/customer/favorite/edit", data)
    },
    getFavorites() {
        return axios
            .get(base.base1 + "/customer/favorites")
    },
    showFavoriteContents(data) {
        return axios
            .get(base.base1 + "/customer/favorite/" + data)
    }
}

export default personalFavorite;