package com.jeanbarrossilva.ongoing.extensions

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider

/**
 * Whether or not there is a node with that matches the given [matcher].
 *
 * @param matcher [SemanticsMatcher] to match the node against.
 **/
internal fun SemanticsNodeInteractionsProvider.hasNodeThat(matcher: SemanticsMatcher): Boolean {
    return try { onNode(matcher).assertExists() } catch (_: AssertionError) { null } != null
}