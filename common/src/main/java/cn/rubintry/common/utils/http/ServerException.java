package cn.rubintry.common.utils.http;

import androidx.annotation.Nullable;

/**
 * @author logcat
 */
public class ServerException extends RuntimeException {
    private int code;
    private String message;

    public ServerException(String message, int code) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
