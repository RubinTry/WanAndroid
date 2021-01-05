package cn.rubintry.common.utils

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import cn.rubintry.common.utils.http.HttpExecutors
import java.util.concurrent.Executor

/**
 * @author logcat
 */
object ToastUtils {
    private var toast: Toast? = null
    private val mMainThread: Executor = MainThreadExecutors()

    fun showShort(message: String) {
        requireNotNull(Utils.applicationContext) { "You doesn't init Utils yet !!!" }
        show(message, Toast.LENGTH_SHORT)
    }

    private fun show(message: String, duration: Int) {
        runOnUiThread{
            if (toast != null) {
                toast!!.cancel()
            }
            toast = Toast.makeText(
                Utils.applicationContext,
                message,
                duration
            )
            toast?.show()
        }
    }



    fun showLong(message: String) {
        requireNotNull(Utils.applicationContext) { "You doesn't init Utils yet !!!" }
        show(message, Toast.LENGTH_LONG)
    }




    private fun runOnUiThread(command: Runnable){
        mMainThread.execute(command)
    }


    private fun runOnUiThread(command: () -> Unit) {
        mMainThread.execute(command)
    }

    private class MainThreadExecutors : Executor {
        private val handler = Handler(Looper.getMainLooper())
        override fun execute(runnable: Runnable) {
            handler.post(runnable)
        }
    }
}