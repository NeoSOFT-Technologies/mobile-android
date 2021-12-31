/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.arch.error

@Suppress("EmptyDefaultConstructor")
class EventsDispatcher<ListenerType : Any>() {
    fun dispatchEvent(block: ListenerType.() -> Unit) {}
}