package piper.im.common.exception;

/**
 * 异常枚举
 *
 * @author piper
 */
public enum IMErrorEnum implements IMBaseError {
    PARAM_ERROR(1000, "参数错误"),
    USER_NOT_FOUND(1001, "用户找不到"),
    INVALID_PWD(1002, "密码不正确"),
    INVALID_TOKEN(1003, "登录失效"),
    SERVER_ERROR(1004, "服务器错误"),
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