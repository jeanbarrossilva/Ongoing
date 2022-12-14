package com.jeanbarrossilva.ongoing.app.extensions

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher

/**
 * Whether or not the node has a test tag that's prefixed with [prefix].
 *
 * @param prefix Prefix to match with the node's test tag's.
 **/
internal fun hasTestTagPrefixedWith(prefix: String): SemanticsMatcher {
    val property = SemanticsProperties.TestTag
    return SemanticsMatcher("${property.name} ∈ ('$prefix']") {
        it.config.getOrNull(property)?.startsWith(prefix) == true
    }
}