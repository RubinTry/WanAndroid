package cn.rubintry.common.utils.http;

/**
 * @author logcat
 */

public enum MethodType {
    SYNCHRONIZE(0),
    ASYNCHRONOUS(1)
    ;
    int type;

    MethodType(int type) {
        this.type = type;
    }
}
