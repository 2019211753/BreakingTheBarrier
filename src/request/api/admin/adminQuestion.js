/**
 * article模块接口列表
 */

import base from "../../base";
import instance from "../../http";


const adminQuestion = {
  searchQuestions(data) {
    return instance
      .post("/admin/searchQuestions", data)
  },
  editeQuestion(data) {
    return instance
      .post("/admin/question/post", data)
  },
  getSearchQuestion(data) {
    return instance
      .get("/admin/question/"+data+"/edit")
  }
}

export default adminQuestion;
