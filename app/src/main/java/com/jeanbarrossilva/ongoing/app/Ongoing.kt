package com.jeanbarrossilva.ongoing.app

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.jeanbarrossilva.ongoing.app.Ongoing.longDuration
import com.jeanbarrossilva.ongoing.app.Ongoing.getAnimationSpec
import com.jeanbarrossilva.ongoing.app.Ongoing.longDelay
import com.jeanbarrossilva.ongoing.app.destination.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

internal object Ongoing {
    private val defaultDuration = AnimationConstants.DefaultDurationMillis.milliseconds
    private val defaultDelay = Duration.ZERO

    val longDuration = defaultDuration + 256.milliseconds
    val longDelay = defaultDelay + 128.milliseconds

    fun getAnimationSpec(
        easing: Easing = FastOutSlowInEasing,
        duration: Duration = defaultDuration,
        delay: Duration = defaultDelay
    ): FiniteAnimationSpec<IntOffset> {
        val durationInMillis = duration.inWholeMilliseconds.toInt()
        val delayInMillis = delay.inWholeMilliseconds.toInt()
        return tween(durationInMillis, delayInMillis, easing = easing)
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
internal fun Ongoing() {
    val engine = rememberAnimatedNavHostEngine(
        rootDefaultAnimations = RootNavGraphDefaultAnimations(
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up, getAnimationSpec())
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Down,
                    getAnimationSpec(FastOutLinearInEasing, longDuration, longDelay)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up,
                    getAnimationSpec(LinearOutSlowInEasing)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Down,
                    getAnimationSpec(FastOutLinearInEasing, longDuration)
                )
            }
        )
    )

    DestinationsNavHost(NavGraphs.root, engine = engine)
}