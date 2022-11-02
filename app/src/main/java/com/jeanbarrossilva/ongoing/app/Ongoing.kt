package com.jeanbarrossilva.ongoing.app

import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.app.destination.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
internal fun Ongoing() {
    DestinationsNavHost(NavGraphs.root)
}