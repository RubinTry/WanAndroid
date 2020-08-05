package cn.rubintry.common.utils.http;

import androidx.annotation.Nullable;

/**
 * @author logcat
 */
public class ConvertException extends RuntimeException {
    private String message;

    public ConvertException(String message) {
        this.message = message;
    }


    @Nullable
    @Override
    public String getMessage() {
        return message;
    }
}
