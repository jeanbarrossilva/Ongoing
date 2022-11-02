package com.jeanbarrossilva.ongoing.feature.activityediting

import android.content.Context
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemorySessionManager
import com.jeanbarrossilva.ongoing.feature.activityediting.component.ActivityNameTextField
import com.jeanbarrossilva.ongoing.feature.activityediting.extensions.createAndroidComposeRule
import com.jeanbarrossilva.ongoing.platform.registry.authorization.CurrentUserIdProvider
import com.jeanbarrossilva.ongoing.platform.registry.extensions.database
import com.jeanbarrossilva.ongoing.platform.registry.extensions.getActivityRegistry
import com.jeanbarrossilva.ongoing.platform.registry.extensions.invoke
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.test.KoinTestRule

internal class ActivityEditingActivityTests {
    private val sessionManager = InMemorySessionManager()
    private val module = module {
        single<ActivityRegistry> {
            database.getActivityRegistry(CurrentUserIdProvider(sessionManager))
        }
    }
    private val mode = ActivityEditingMode.Modification(ContextualActivity.sample)

    private val instrumentation
        get() = InstrumentationRegistry.getInstrumentation()
    private val context
        get() = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule(order = 0)
    val koinRule = KoinTestRule.create {
        androidContext(context)
        modules(module)
    }

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<ActivityEditingActivity>(
        context,
        ActivityEditingActivity.MODE_KEY to mode
    )

    @Test
    fun showConfirmationDialogOnBackPressWhenModifying() {
        composeRule.onNodeWithTag(ActivityNameTextField.TAG).performTextInput(" :)")
        instrumentation.runOnMainSync {
            composeRule.activity.onBackPressedDispatcher.onBackPressed()
        }
        onView(withText(R.string.feature_activity_editing_confirmation_dialog_title))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }
}