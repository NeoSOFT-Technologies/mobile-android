package com.arch.error.presenters


open class BaseSelectorErrorPresenter<T : Any>(
    errorPresenterSelector: (Throwable) -> BaseErrorPresenter<T>
) : BaseErrorPresenter<T>
