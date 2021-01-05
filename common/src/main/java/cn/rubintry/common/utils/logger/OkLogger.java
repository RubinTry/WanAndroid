package cn.rubintry.common.utils.logger;

/**
 * @author logcat
 */
public class OkLogger {
    static ILogger logger = new Logger(OkLogger.class.getSimpleName());

    public static void debug(String message){
        logger.debug(message);
    }

    public static void debug(String message , Throwable ex){
        logger.debug(message , ex);
    }


    public static void error(String message){
        logger.error(message);
    }

    public static void error(String message , Throwable ex){
        logger.error(message , ex);
    }


    public static void warn(String message){
        logger.warn(message);
    }

    public static void warn(String message , Throwable ex){
        logger.warn(message , ex);
    }


    public static void verbose(String message){
        logger.verbose(message);
    }

    public static void verbose(String message , Throwable ex){
        logger.verbose(message , ex);
    }

}
