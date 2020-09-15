package cn.rubintry.common.utils.logger;

import android.util.Log;

/**
 * @author logcat
 */
public class Logger implements ILogger {
    private String TAG;

    public Logger(String TAG) {
        this.TAG = TAG;
    }

    @Override
    public void debug(String message) {
        Log.d(TAG, message);
    }

    @Override
    public void debug(String message, Throwable ex) {
        Log.d(TAG, message , ex);
    }

    @Override
    public void error(String message) {
        Log.e(TAG, message);
    }

    @Override
    public void error(String message, Throwable ex) {
        Log.e(TAG, message , ex);
    }

    @Override
    public void warn(String message) {
        Log.w(TAG, message);
    }

    @Override
    public void warn(String message, Throwable ex) {
        Log.w(TAG, message , ex);
    }

    @Override
    public void verbose(String message) {
        Log.v(TAG , message);
    }

    @Override
    public void verbose(String message, Throwable ex) {
        Log.v(TAG , message , ex);
    }
}
