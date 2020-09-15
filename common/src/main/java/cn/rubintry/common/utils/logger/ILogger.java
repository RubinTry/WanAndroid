package cn.rubintry.common.utils.logger;

/**
 * @author logcat
 */
public interface ILogger {
    void debug(String message);

    void debug(String message , Throwable ex);

    void error(String message);

    void error(String message , Throwable ex);


    void warn(String message);

    void warn(String message , Throwable ex);

    void verbose(String message);

    void verbose(String message , Throwable ex);
}
