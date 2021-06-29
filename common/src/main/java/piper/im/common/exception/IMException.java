package piper.im.common.exception;

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
