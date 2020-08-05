package cn.rubintry.common

import androidx.fragment.app.FragmentActivity
import kotlin.system.exitProcess

class ActivityMonitor {


    private var activityList : ArrayList<FragmentActivity> = ArrayList()

    companion object {
        @Volatile
        var instance: ActivityMonitor? = null
            get() {
                if (field == null) {
                    synchronized(ActivityMonitor::class.java) {
                        if (field == null) {
                            field = ActivityMonitor()
                        }
                    }
                }
                return field
            }
            private set
    }


    fun add(activity: FragmentActivity){
        activityList.add(activity)
    }


    fun remove(activity: FragmentActivity){
        activityList.remove(activity)
    }

    fun getCurrentActivity() : FragmentActivity ?{
        return activityList.last()
    }

    fun exit(){
        for (activity in activityList){
            activity.finish()
        }
        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(0)
    }
}