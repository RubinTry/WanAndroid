package cn.rubintry.common.utils.http;

/**
 * @author logcat
 */

public enum RequestType {
    GET(0),
    POST(1)
    ;
    int type;

    RequestType(int type) {
        this.type = type;
    }
}
