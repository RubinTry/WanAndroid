package cn.rubintry.common.utils;

import java.lang.reflect.Field;

/**
 * @author logcat
 * 属性值工具类
 */
public class ClassUtils {


    /**
     * 取出object对象中属性名为propertyName的属性值
     * @param object
     * @param propertyName
     * @return
     */
    public static Object getPropertyValue(Object object, String propertyName) {

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals(propertyName)) {
                try {
                    return field.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }


    public static Object getNewInstance(String className){
        if(className == null){
            return null;
        }
        try {
            Class aClass = Class.forName(className);
            return aClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
