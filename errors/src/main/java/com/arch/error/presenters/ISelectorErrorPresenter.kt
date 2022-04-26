package com.arch.error.presenters


open class ISelectorErrorPresenter<T : Any>(
    errorPresenterSelectorI: (Throwable) -> IErrorPresenter<T>
) : IErrorPresenter<T>
