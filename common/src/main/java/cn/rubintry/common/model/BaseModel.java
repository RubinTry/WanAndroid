package cn.rubintry.common.model;

import cn.rubintry.common.utils.http.IMonitor;

/**
 * @author logcat
 */
public class BaseModel<T> implements IMonitor {
    private T data;
    private int errorCode;
    private String errorMsg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }



    @Override
    public String getErrMsg() {
        return errorMsg;
    }

    @Override
    public int getCode() {
        return errorCode;
    }

    @Override
    public boolean success() {
        return errorCode == 0;
    }
}
