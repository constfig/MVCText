package cn.moquan.util;

/**
 * describe
 *
 * @author wangyuanhong
 * @date 2021/10/19
 */

public class CommonResponseBody {
    private int state;
    private String message;
    private Object data;

    public CommonResponseBody() {
    }

    public CommonResponseBody(int state) {
        this.state = state;
    }

    public CommonResponseBody(int state, String message) {
        this.state = state;
        this.message = message;
    }

    public CommonResponseBody(int state, Object data) {
        this.state = state;
        this.data = data;
    }

    public CommonResponseBody(int state, String message, Object data) {
        this.state = state;
        this.message = message;
        this.data = data;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommonResponseBody{" +
                "state=" + state +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
