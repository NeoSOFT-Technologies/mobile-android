package com.arch.template.base

import androidx.lifecycle.ViewModel
import com.core.utils.ResourceString
import com.core.utils.SingleLiveEvent
import com.core.utils.TextResourceString


open class BaseViewModel : ViewModel() {
    internal val toast = SingleLiveEvent<ResourceString>()

    fun showToastWithString(str: String) {
        toast.value = TextResourceString(str)
    }

    fun showToastWithStringAsync(str: String) {
        toast.postValue(TextResourceString(str))
    }
}
