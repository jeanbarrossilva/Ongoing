package com.jeanbarrossilva.ongoing.feature.settings.extensions

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.jeanbarrossilva.ongoing.feature.settings.component.activities.clear.ActivitiesClearSetting
import com.jeanbarrossilva.ongoing.feature.settings.component.activities.clear.confirmation.ActivitiesClearConfirmationButton

/** Asks for the activities to be cleared and then confirms the request. **/
internal fun ComposeTestRule.clearActivities() {
    onClearActivitiesSetting().performClick()
    onClearActivitiesConfirmationButton().performClick()
}

/** [SemanticsNodeInteraction] for the setting that clears all the registered activities. **/
internal fun ComposeTestRule.onClearActivitiesSetting(): SemanticsNodeInteraction {
    return onNodeWithTag(ActivitiesClearSetting.TAG)
}

/**
 * [SemanticsNodeInteraction] for the confirmation button in the dialog that asks the user about
 * their certainty on whether they want to clear their activities or not.
 **/
internal fun ComposeTestRule.onClearActivitiesConfirmationButton(): SemanticsNodeInteraction {
    return onNodeWithTag(ActivitiesClearConfirmationButton.TAG)
}