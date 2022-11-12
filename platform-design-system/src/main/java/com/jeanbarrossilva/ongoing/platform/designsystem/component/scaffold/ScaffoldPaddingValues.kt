package com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold

import androidx.compose.foundation.layout.PaddingValues

abstract class ScaffoldPaddingValues internal constructor(): PaddingValues {
    abstract val bars: PaddingValues
    abstract val fab: PaddingValues
}