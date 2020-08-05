package cn.rubintry.common.utils.http;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;

/**
 * @author logcat
 */
public class HttpTask {
    private String interfacePath;
    private RequestType requestType;
    private MethodType methodType;
    private Map<String , String> parameters;
    private MediaType mediaType;

    public HttpTask() {
        if(parameters == null){
            parameters = new HashMap<>();
        }
    }

    public MediaType getMediaType() {
        if(mediaType == null){
            return MediaTypePool.DEFAULT;
        }
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getInterfacePath() {
        return interfacePath;
    }

    public void setInterfacePath(String interfacePath) {
        this.interfacePath = interfacePath;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public MethodType getMethodType() {
        //默认使用同步请求
        if(methodType == null){
            return MethodType.ASYNCHRONOUS;
        }
        return methodType;
    }

    public void setMethodType(MethodType methodType) {
        this.methodType = methodType;
    }

    public Map<String, String> getParameters() {
        if(parameters == null){
            parameters = new HashMap<>();
        }
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}
