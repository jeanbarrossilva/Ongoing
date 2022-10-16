package com.jeanbarrossilva.ongoing.platform.designsystem.core.viewmodel

import androidx.lifecycle.ViewModel
import kotlin.reflect.KClass

internal class InvalidViewModelException(
    originalClass: KClass<ViewModel>,
    givenClass: KClass<ViewModel>
): IllegalArgumentException(
    "Cannot create an instance of ${givenClass.simpleName} because it's not a " +
        "${originalClass.qualifiedName}."
)