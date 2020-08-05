package cn.rubintry.common.utils.http;

import android.util.Log;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.rubintry.common.utils.GsonUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.tls.HandshakeCertificates;

/**
 * @author logcat
 */
public class OkHttpUtils<T> {
    private static final int RESPONSE_OK = 200;
    private static volatile OkHttpUtils instance;
    private HttpTask httpTask;
    private BaseHttp config;

    private OkHttpClient defaultClient;
    private String TAG = this.getClass().getSimpleName();

    private OkHttpUtils() {
        httpTask = new HttpTask();
    }

    public static OkHttpUtils getInstance() {
        if (instance == null) {
            synchronized (OkHttpUtils.class) {
                if (instance == null) {
                    instance = new OkHttpUtils();

                }
            }
        }

        return instance;
    }

    /**
     * 初始化参数
     *
     * @param config
     */
    public void initialize(BaseHttp config) {
        this.config = config;
    }


    /**
     * 设置mediaType
     *
     * @param mediaType
     * @return
     */
    public OkHttpUtils setMediaType(MediaType mediaType) {
        httpTask.setMediaType(mediaType);
        return this;
    }


    /**
     * 添加参数
     *
     * @param key
     * @param value
     * @return
     */
    public OkHttpUtils addParameters(String key, String value) {
        httpTask.getParameters().put(key, value);
        return this;
    }


    /**
     * 将请求方式设为get
     *
     * @param path
     * @return
     */
    public OkHttpUtils get(String path) {
        get(path, null);
        return this;
    }


    /**
     * 将请求方式设为get并携带参数
     *
     * @param path
     * @param parameters
     * @return
     */
    public OkHttpUtils get(String path, Map<String, String> parameters) {
        httpTask.setInterfacePath(path);
        httpTask.setRequestType(RequestType.GET);
        httpTask.setParameters(parameters);
        return this;
    }

    /**
     * 将请求方式设为post
     *
     * @return
     */
    public OkHttpUtils post(String path) {
        post(path, null);
        return this;
    }

    /**
     * 将请求方式设为post并携带参数
     *
     * @param path
     * @param parameters
     * @return
     */
    public OkHttpUtils post(String path, Map<String, String> parameters) {
        httpTask.setInterfacePath(path);
        httpTask.setRequestType(RequestType.POST);
        httpTask.setParameters(parameters);
        return this;
    }

    /**
     * 请求方式  SYNCHRONIZE 同步请求  ASYNCHRONOUS 异步请求
     *
     * @param methodType
     * @return
     */
    public OkHttpUtils setMethodType(MethodType methodType) {
        httpTask.setMethodType(methodType);
        return this;
    }

    /**
     * 发起一个请求
     *
     * @param responseCallback
     */
    public void request(final ResponseCallback<T> responseCallback) {

        final Request.Builder builder = new Request.Builder();

        //获取请求类型
        switch (httpTask.getRequestType()) {
            case GET:
                String getUrl = getWholeUrl(config.getBaseUrl(), httpTask.getInterfacePath(), httpTask.getParameters());
                builder.url(getUrl).get();
                break;
            case POST:
                //使用form表单方式进行参数拼接
                Map<String, String> parameters = httpTask.getParameters();
                FormBody.Builder bodyBuilder = new FormBody.Builder(Charset.defaultCharset());
                for (String key : parameters.keySet()) {
                    bodyBuilder.add(key, parameters.get(key));
                }

                String postUrl = config.getBaseUrl() + httpTask.getInterfacePath();
                builder.url(postUrl).post(bodyBuilder.build());

                break;
            default:
                break;
        }

        //使用专门的网络请求线程池进行请求网络
        HttpExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Type modelType = responseCallback.getModelType();
                    try {
                        if (modelType == null) {
                            throw new IllegalArgumentException("No type-model , please set it!!!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (httpTask.getMethodType() == MethodType.SYNCHRONIZE) {
                        //这里发起同步请求
                        execute(builder.build(), config, responseCallback, modelType);
                    } else {
                        //这里发起异步请求
                        enqueue(builder.build(), config, responseCallback, modelType);

                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception happen : ", e);
                }
            }
        });

    }

    private void enqueue(Request request, BaseHttp config, ResponseCallback<T> responseCallback, Type modelType) {
        Call call = getDefaultClient(config).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if (responseCallback != null) {
                    responseCallback.onFailed(e , null);
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseJsonStr = response.body().string();
                Object bodyObj = GsonUtils.getDefaultGson().fromJson(responseJsonStr, modelType);
                if(IMonitor.class.isAssignableFrom(bodyObj.getClass())){
                    IMonitor iMonitor = (IMonitor) bodyObj;
                    if(iMonitor.success()){
                        if (responseCallback != null) {
                            responseCallback.onSuccess((T) bodyObj);
                        }
                    }else{
                        if(responseCallback != null){
                            responseCallback.onFailed(new ServerException(iMonitor.getErrMsg() , iMonitor.getCode()) , (T) iMonitor);
                        }
                    }
                }else{
                    response.body().close();
                    throw new IllegalAccessError("Please implements interface IMonitor before use this Util");
                }

                response.body().close();
            }
        });
    }


    /**
     * 同步执行请求
     *
     * @param request   发起的请求
     * @param config    http基本配置
     * @param callback  响应回调函数
     * @param modelType 响应的数据的泛型
     * @throws IOException
     */
    private void execute(Request request, BaseHttp config, ResponseCallback<T> callback, Type modelType) throws IOException {
        Call call = getDefaultClient(config).newCall(request);

        Response response = call.execute();
        //request success
        String responseJsonStr = response.body().string();
        //Convert response info to result object.
        Object obj = GsonUtils.getDefaultGson().fromJson(responseJsonStr, modelType);
        if (response.code() == RESPONSE_OK) {

            if(IMonitor.class.isAssignableFrom(obj.getClass())){
                IMonitor iMonitor = (IMonitor) obj;
                if(iMonitor.success()){
                    if (callback != null) {
                        callback.onSuccess((T) obj);
                    }
                }else{
                    if (callback != null) {

                        callback.onFailed(new ConvertException("Convert failed , target type is" + modelType.getTypeName() + " but jsonStr is " + responseJsonStr) , (T) iMonitor);
                    }
                }
            }else{
                response.body().close();
                throw new IllegalAccessError("Please implements interface IMonitor before use this Util");
            }
            response.body().close();
        } else {
            Log.e(TAG, "no response , result code is " + response.code());
            if (callback != null) {
                if(IMonitor.class.isAssignableFrom(obj.getClass())){
                    IMonitor iMonitor = (IMonitor) obj;
                    callback.onFailed(new ConvertException("Convert failed , target type is" + modelType.getTypeName() + " but jsonStr is " + responseJsonStr) , (T) iMonitor);
                }
            }
        }

    }


    /**
     * 拼接完整的url地址（可带参数，一般给get模式用来请求）
     *
     * @param baseUrl       主机地址
     * @param interfaceName 接口名称（路径）
     * @param parameters    需要拼接进去的参数
     * @return
     */
    private String getWholeUrl(String baseUrl, String interfaceName, Map<String, String> parameters) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(baseUrl).append(interfaceName).append("?");
        if (parameters != null) {
            for (String key : parameters.keySet()) {
                stringBuilder.append(key).append("=").append(parameters.get(key)).append("&");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }


    private OkHttpClient getDefaultClient(BaseHttp config) {
        if (defaultClient == null) {
            synchronized (OkHttpClient.class) {
                if (defaultClient == null) {
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(@NotNull String message) {
                            try {
                                //Convert to UTF-8 encoding, otherwise garbled characters will be displayed in logcat
                                String msgText = URLDecoder.decode(message, "utf-8");
                                Log.d(TAG, msgText);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });


                    //携带一个握手证书
                    HandshakeCertificates clientCertificates = new HandshakeCertificates.Builder()
                            .addPlatformTrustedCertificates()
                            .build();

                    //开启日志打印
                    httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

                    defaultClient = new OkHttpClient.Builder()
                            .sslSocketFactory(clientCertificates.sslSocketFactory(), clientCertificates.trustManager())
                            .connectTimeout(config.getConnectTimeout(), TimeUnit.MILLISECONDS)
                            .readTimeout(config.getReadTimeout(), TimeUnit.MILLISECONDS)
                            .writeTimeout(config.getWriteTimeout(), TimeUnit.MILLISECONDS)
                            .addInterceptor(httpLoggingInterceptor).build();
                }
            }
        }
        return defaultClient;
    }


}
