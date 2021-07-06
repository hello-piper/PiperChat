package piper.im.common.exception;

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

    public static <T> IMResult<T> success(T data) {
        IMResult<T> imResult = new IMResult<>();
        imResult.setCode(0);
        imResult.setMsg("success");
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
