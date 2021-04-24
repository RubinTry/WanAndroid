package cn.rubintry.lib_common.thread

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

class DefaultThreadFactory() : ThreadFactory {
    private val poolNumber = AtomicInteger(1)

    private val threadNumber = AtomicInteger(1)
    private var group: ThreadGroup? = null
    private var namePrefix: String? = null

    init {
        val s = System.getSecurityManager()
        group = if (s != null) s.threadGroup else Thread.currentThread().threadGroup
        namePrefix = "WanAndroid task pool No." + poolNumber.getAndIncrement() + ", thread No."
    }
    override fun newThread(runnable : Runnable?): Thread {
        val threadName = namePrefix + threadNumber.getAndIncrement()
        Logger.info("Thread production, name is [$threadName]")
        val thread = Thread(group, runnable, threadName, 0)
        if (thread.isDaemon) {   //设为非后台线程
            thread.isDaemon = false
        }
        if (thread.priority != Thread.NORM_PRIORITY) { //优先级为normal
            thread.priority = Thread.NORM_PRIORITY
        }

        // 捕获多线程处理中的异常
        thread.uncaughtExceptionHandler = Thread.UncaughtExceptionHandler { thread, ex -> Logger.info("Running task appeared exception! Thread [" + thread.name + "], because [" + ex.message + "]") }
        return thread
    }
}