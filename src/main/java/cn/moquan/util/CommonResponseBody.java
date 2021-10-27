package cn.moquan.util;

/**
 * describe
 *
 * @author wangyuanhong
 * @date 2021/10/19
 */

public class CommonResponseBody {
    private int status;
    private String message;
    private Object data;

    public CommonResponseBody() {
    }

    public CommonResponseBody(int status) {
        this.status = status;
    }

    public CommonResponseBody(int status, Object data) {
        this.status = status;
        this.data = data;
    }

    public CommonResponseBody(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public CommonResponseBody(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public CommonResponseBody(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
