package goja.wxchat.exceptions;

/**
 * <p>
 * 异常
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-02-12 13:41
 * @since JDK 1.6
 */
public class WechatException extends RuntimeException {

    private static final long serialVersionUID = 864266206986172260L;

    public WechatException() {
        super();
    }

    public WechatException(String message) {
        super(message);
    }

    public WechatException(String message, Throwable cause) {
        super(message, cause);
    }

}
