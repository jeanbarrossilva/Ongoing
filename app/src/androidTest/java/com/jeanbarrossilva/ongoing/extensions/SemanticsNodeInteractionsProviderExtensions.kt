package com.jeanbarrossilva.ongoing.extensions

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.onNodeWithTag

/**
 * Whether or not there is a node with the given [testTag].
 *
 * @param testTag Test tag of the node to look for.
 **/
internal fun SemanticsNodeInteractionsProvider.hasNodeWithTag(testTag: String): Boolean {
    return try { onNodeWithTag(testTag).assertExists() } catch (_: AssertionError) { null } != null
}