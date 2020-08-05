package cn.rubintry.common.utils

import android.widget.Toast

/**
 * @author logcat
 */
object ToastUtils {
    private var toast: Toast? = null
    fun showShort(message: String) {
        requireNotNull(Utils.getApplicationContext()) { "You doesn't init Utils yet !!!" }
        show(message, Toast.LENGTH_SHORT)
    }

    private fun show(message: String, duration: Int) {
        if (toast != null) {
            toast!!.cancel()
        }
        toast = Toast.makeText(
            Utils.getApplicationContext(),
            message,
            duration
        )
        toast?.show()
    }

    fun showLong(message: String) {
        requireNotNull(Utils.getApplicationContext()) { "You doesn't init Utils yet !!!" }
        show(message, Toast.LENGTH_LONG)
    }
}