package cn.rubintry.common.utils;

import com.google.gson.Gson;

/**
 * @author logcat
 */
public class GsonUtils {
    private volatile static Gson defaultGson;

    public static Gson getDefaultGson(){
        if(defaultGson == null){
            synchronized (Gson.class){
                if(defaultGson == null){
                    defaultGson = new Gson();
                }
            }
        }
        return defaultGson;
    }
}
