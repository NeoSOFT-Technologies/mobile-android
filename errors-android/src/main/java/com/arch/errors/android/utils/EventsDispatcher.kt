

package com.arch.errors.android.utils

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.arch.error.IEventsDispatcher
import java.util.concurrent.Executor


fun createExecutorOnMainLooper(): Executor {
    val mainLooper = Looper.getMainLooper()
    val mainHandler = Handler(mainLooper)
    return Executor { mainHandler.post(it) }
}

inline fun <reified T : Any> eventsDispatcherOnMain(): EventsDispatcher<T> {
    return EventsDispatcher(createExecutorOnMainLooper())
}

class EventsDispatcher<ListenerType : Any> : IEventsDispatcher<ListenerType> {
    private var eventsListener: ListenerType? = null
    private val blocks = mutableListOf<ListenerType.() -> Unit>()
    private val executor: Executor

    constructor() {
        this.executor = createExecutorOnMainLooper()
    }

    constructor(executor: Executor) {
        this.executor = executor
    }

    /**
     * Constructor without lifecycle connection. Used for tests
     */
    constructor(executor: Executor, listener: ListenerType) {
        this.executor = executor
        this.eventsListener = listener
    }

    fun bind(lifecycleOwner: LifecycleOwner, listener: ListenerType) {
        val observer = object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun connectListener() {
                eventsListener = listener
                executor.execute {
                    blocks.forEach { it(listener) }
                    blocks.clear()
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun disconnectListener() {
                eventsListener = null
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroyed(source: LifecycleOwner) {
                source.lifecycle.removeObserver(this)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
    }

    override fun dispatchEvent(block: ListenerType.() -> Unit) {
        val eListener = eventsListener
        if (eListener != null) {
            executor.execute { block(eListener) }
        } else {
            executor.execute { blocks.add(block) }
        }
    }
}


