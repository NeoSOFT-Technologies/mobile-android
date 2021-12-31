

package com.arch.error.mappers

import kotlin.reflect.KClass

class FallbackValueNotFoundException(val clazz: KClass<*>) :
    Exception("There is no fallback value for class [$clazz]")
