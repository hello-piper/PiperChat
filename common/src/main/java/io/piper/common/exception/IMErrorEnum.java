/*
 * Copyright (c) 2020-2030 The Piper(https://github.com/hello-piper)
 *
 * The PiperChat is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *
 * http://license.coscl.org.cn/MulanPSL2
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package io.piper.common.exception;

/**
 * IMErrorEnum
 *
 * @author piper
 */
public enum IMErrorEnum implements IMBaseError {
    SERVER_ERROR(1000, "服务器错误"),
    INVALID_TOKEN(1001, "登录失效"),
    PARAM_ERROR(1002, "参数错误"),
    USER_NOT_FOUND(1003, "用户找不到"),
    INVALID_PWD(1004, "密码不正确"),
    INVALID_VERIFY_CODE(1005, "验证码不正确"),
    ;

    IMErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
