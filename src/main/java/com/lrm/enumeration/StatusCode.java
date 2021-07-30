package com.lrm.enumeration;


/**
 * 状态码枚举类
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-29
 */
public enum StatusCode {
    /*
     * 406 定义为操作失败 如改名已有同名昵称了所以没改成功等等（不是跳转到异常页面，而是直接在当前页给出提示）
     * 405 定义为参数校验失败 如登录没写密码（不是跳转到异常页面，而是直接在当前页给出提示）
     * 404 定义为没找到对应资源
     * 403 定义为无权限访问
     * 402 定义为文件过大或输入输出流异常
     * 401 定义为JWT鉴权失败
     * 400 定义其他未知错误
     * 200 为操作成功
     */
    FAILED_OPERATION(406),
    ILLEGAL_PARAMETER(405),
    NOT_FOUND(404),
    NO_PERMISSION(403),
    FAILED_TRANSFER(402),
    WRONG_JWT(401),
    UNKNOWN(400),
    SUCCESS(200);

    Integer code;

    StatusCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code.toString();
    }

}