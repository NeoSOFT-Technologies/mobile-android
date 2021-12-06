package com.arch.template.base

import androidx.lifecycle.ViewModel
import com.arch.template.utils.ResourceString
import com.arch.template.utils.SingleLiveEvent
import com.arch.template.utils.TextResourceString


open class BaseViewModel : ViewModel() {
    internal val toast = SingleLiveEvent<ResourceString>()

    fun showToastWithString(str: String) {
        toast.value = TextResourceString(str)
    }

    fun showToastWithStringAsync(str: String) {
        toast.postValue(TextResourceString(str))
    }
}
