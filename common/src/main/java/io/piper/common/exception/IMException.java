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
 * 封装异常
 *
 * @author piper
 */
public class IMException extends RuntimeException {
    private static final long serialVersionUID = 1;

    private int code;
    private String msg;

    public IMException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public IMException(IMBaseError e) {
        this.code = e.getCode();
        this.msg = e.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static IMException build(IMBaseError e) {
        return new IMException(e);
    }

    public static IMException build(IMBaseError e, String msg) {
        return new IMException(e.getCode(), msg);
    }
}
