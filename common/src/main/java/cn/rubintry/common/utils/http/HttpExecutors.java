package cn.rubintry.common.utils.http;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author logcat
 */
public class HttpExecutors {

    private final Executor mDiskIO;
    private final Executor mNetworkIO;
    private final Executor mMainThread;

    private static volatile HttpExecutors instance;

    public static HttpExecutors getInstance(){
        if(instance == null){
            synchronized (HttpExecutors.class){
                if(instance == null){
                    instance = new HttpExecutors();
                }
            }
        }
        return instance;
    }

    public HttpExecutors(Executor mDiskIO, Executor mNetworkIO, Executor mMainThread) {
        this.mDiskIO = mDiskIO;
        this.mNetworkIO = mNetworkIO;
        this.mMainThread = mMainThread;
    }

    public Executor diskIO() {
        return mDiskIO;
    }

    public Executor networkIO() {
        return mNetworkIO;
    }

    public Executor mainThread() {
        return mMainThread;
    }

    public HttpExecutors(){
        this(Executors.newSingleThreadExecutor() , Executors.newFixedThreadPool(3) , new MainThreadExecutors());
    }



    private static class MainThreadExecutors  implements Executor {

        private Handler handler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable runnable) {
            handler.post(runnable);
        }
    }

}
