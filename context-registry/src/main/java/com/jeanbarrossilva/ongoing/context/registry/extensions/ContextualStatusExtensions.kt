package com.jeanbarrossilva.ongoing.context.registry.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualStatus

val ContextualStatus.title
    @Composable get() = stringResource(titleRes)