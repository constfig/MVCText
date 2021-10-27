package cn.moquan.util;

/**
 * describe
 *
 * @author wangyuanhong
 * @date 2021/10/25
 */
public class ThrowExceptionUtil {

    public static void throwRollBackException(boolean flag, String message){
        if(!flag){
            throw new RollBackException(message,
                    new CommonResponseBody(StateNumber.FAILED, message));
        }
    }

}
