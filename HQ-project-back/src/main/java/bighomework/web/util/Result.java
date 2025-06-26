package bighomework.web.util;

public class Result {
    private String msg;
    private int code;
    public Result(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }
    //正确就返回200，错误就返回400
    public static Result FroFront(String msg,int code) {
        return new Result(msg,code);
    }

    public String getMsg() {
        return msg;
    }
    public int getCode() {
        return code;
    }
}
