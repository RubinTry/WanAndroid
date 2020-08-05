package cn.rubintry.common.utils.http;


import java.util.concurrent.TimeUnit;

/**
 * @author logcat
 */
public class BaseHttp {
    private String baseUrl;
    private Long connectTimeout;
    private Long readTimeout;
    private Long writeTimeout;

    public BaseHttp() {
    }

    public BaseHttp(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Long connectTimeout) {
        setConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
    }


    public void setConnectTimeout(Long connectTimeout, TimeUnit unit) {
        this.connectTimeout = getTimeOut(connectTimeout, unit);
    }

    public Long getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Long readTimeout) {
        setReadTimeout(readTimeout, TimeUnit.MILLISECONDS);
    }

    public void setReadTimeout(Long readTimeout, TimeUnit unit) {
        this.readTimeout = getTimeOut(readTimeout, unit);
    }


    public Long getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(Long writeTimeout) {
        setWriteTimeout(writeTimeout, TimeUnit.MILLISECONDS);
    }


    public void setWriteTimeout(Long writeTimeout, TimeUnit unit) {
        this.writeTimeout = getTimeOut(writeTimeout, unit);
    }

    private Long getTimeOut(Long timeout, TimeUnit unit) {
        long resultTime = 0;
        switch (unit) {
            case NANOSECONDS:
                resultTime = unit.convert(timeout, TimeUnit.NANOSECONDS);
                break;
            case MICROSECONDS:
                resultTime = unit.convert(timeout, TimeUnit.MICROSECONDS);
                break;
            case MILLISECONDS:
                resultTime = unit.convert(timeout, TimeUnit.MILLISECONDS);
                break;
            case SECONDS:
                resultTime = unit.convert(timeout, TimeUnit.SECONDS);
                break;
            case MINUTES:
                resultTime = unit.convert(timeout, TimeUnit.MINUTES);
                break;
            case HOURS:
                resultTime = unit.convert(timeout, TimeUnit.HOURS);
                break;
            case DAYS:
                resultTime = unit.convert(timeout, TimeUnit.DAYS);
                break;
            default:
                break;
        }
        return resultTime;
    }
}
