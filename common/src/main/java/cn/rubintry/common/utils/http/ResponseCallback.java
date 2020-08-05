package cn.rubintry.common.utils.http;

import android.util.Log;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author logcat
 */
public abstract class ResponseCallback<T> {

    private String TAG = this.getClass().getSimpleName();


    /**
     * 请求成功
     *
     * @param t
     */
    protected abstract void onSuccess(T t);


    /**
     * 请求失败
     *
     * @param e
     */
    protected abstract void onFailed(Exception e, T t);


    /**
     * 反射获得泛型对应的type
     *
     * @return
     */
    public Type getModelType() {
        @SuppressWarnings("rawtypes")
        Class clazz = getClass();

        while (clazz != Object.class) {
            Type t = clazz.getGenericSuperclass();
            if (t instanceof ParameterizedType) {
                Type[] args = ((ParameterizedType) t).getActualTypeArguments();
                return args[0];
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }


}
