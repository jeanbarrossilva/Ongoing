package com.jeanbarrossilva.ongoing.platform.designsystem.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KClass

abstract class ViewModelFactory<T: ViewModel>: ViewModelProvider.Factory {
    protected abstract val viewModelClass: KClass<T>

    protected abstract fun create(): T

    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass == viewModelClass.java) {
            create() as T
        } else {
            throw InvalidViewModelException(
                viewModelClass as KClass<ViewModel>,
                modelClass.kotlin as KClass<ViewModel>
            )
        }
    }
}