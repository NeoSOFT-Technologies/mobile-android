

package com.arch.error

@Suppress("EmptyDefaultConstructor")
abstract class BaseEventsDispatcher<ListenerType : Any>() {
    abstract fun dispatchEvent(block: ListenerType.() -> Unit)
}