package cn.rubintry.common.utils;

import android.app.Application;
import android.content.Context;

/**
 * @author logcat
 */
public class Utils {

    private static Context applicationContext;
    public static void init(Application application){
        Utils.applicationContext = application.getApplicationContext();
    }

    public static Context getApplicationContext() {
        return applicationContext;
    }
}
