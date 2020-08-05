package cn.rubintry.common.utils.http

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * @author logcat
 */
class HttpExecutors @JvmOverloads constructor(
    private val mDiskIO: Executor = Executors.newSingleThreadExecutor(),
    private val mNetworkIO: Executor = Executors.newFixedThreadPool(
        3
    ),
    private val mMainThread: Executor = MainThreadExecutors()
) {
    fun diskIO(): Executor {
        return mDiskIO
    }

    fun networkIO(): Executor {
        return mNetworkIO
    }

    fun mainThread(): Executor {
        return mMainThread
    }

    private class MainThreadExecutors : Executor {
        private val handler = Handler(Looper.getMainLooper())
        override fun execute(runnable: Runnable) {
            handler.post(runnable)
        }
    }

    companion object {
        @Volatile
        var instance: HttpExecutors? = null
            get() {
                if (field == null) {
                    synchronized(HttpExecutors::class.java) {
                        if (field == null) {
                            field = HttpExecutors()
                        }
                    }
                }
                return field
            }
            private set
    }

}