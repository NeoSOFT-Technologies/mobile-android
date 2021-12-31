/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.arch.error

@Suppress("EmptyDefaultConstructor")
abstract class BaseEventsDispatcher<ListenerType : Any>() {
    abstract fun dispatchEvent(block: ListenerType.() -> Unit)
}