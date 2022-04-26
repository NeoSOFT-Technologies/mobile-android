

package com.arch.error


interface IEventsDispatcher<ListenerType : Any> {
    fun dispatchEvent(block: ListenerType.() -> Unit)
}