package cn.rubintry.common.utils;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;

/**
 * @author logcat
 */
public class GsonUtils {
    private static volatile GsonUtils instance;

    private static volatile Gson defaultJson;

    public static GsonUtils getDefault(){
        if(instance == null){
            synchronized (GsonUtils.class){
                if(instance == null){
                    instance = new GsonUtils();
                }
            }
        }
        return instance;
    }

    @NotNull
    public Object analyze(@NotNull String responseJsonStr, @NotNull ParameterizedType modelType) {
        return getDefaultJson().fromJson(responseJsonStr , modelType);
    }


    private static Gson getDefaultJson(){
        if(defaultJson  == null){
            synchronized (Gson.class){
                if(defaultJson == null){
                    defaultJson = new Gson();
                }
            }
        }
        return defaultJson;
    }

}
