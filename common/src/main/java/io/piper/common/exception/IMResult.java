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

import cn.hutool.json.JSONUtil;

/**
 * rest返回
 *
 * @author piper
 */
public class IMResult<T> {
    public int code;
    public String msg;
    public T data;

    public static IMResult<Void> ok() {
        IMResult<Void> imResult = new IMResult<>();
        imResult.setCode(0);
        imResult.setMsg("ok");
        return imResult;
    }

    public static <T> IMResult<T> ok(T data) {
        IMResult<T> imResult = new IMResult<>();
        imResult.setCode(0);
        imResult.setMsg("ok");
        imResult.setData(data);
        return imResult;
    }

    public static IMResult<Void> error(String msg) {
        IMResult<Void> imResult = new IMResult<>();
        imResult.setCode(IMErrorEnum.SERVER_ERROR.getCode());
        imResult.setMsg(msg);
        return imResult;
    }

    public static IMResult<Void> error(IMException e) {
        IMResult<Void> imResult = new IMResult<>();
        imResult.setCode(e.getCode());
        imResult.setMsg(e.getMsg());
        return imResult;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
