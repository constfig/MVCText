package cn.moquan.util;

import java.util.List;

/**
 * describe
 *
 * @author wangyuanhong
 * @date 2021/10/21
 */
public class RollBackException extends RuntimeException{

    CommonResponseBody errorInfo;

    public RollBackException(String message, CommonResponseBody errorInfo){
        super(message);
        this.errorInfo = errorInfo;
    }

    public CommonResponseBody getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(CommonResponseBody errorInfo) {
        this.errorInfo = errorInfo;
    }
}
