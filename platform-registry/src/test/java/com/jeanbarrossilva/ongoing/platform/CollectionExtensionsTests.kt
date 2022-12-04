package com.jeanbarrossilva.ongoing.platform

import com.jeanbarrossilva.ongoing.platform.registry.extensions.joinToFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class CollectionExtensionsTests {
    @Test
    fun `GIVEN a collection WHEN joining it to a flow THEN it's up-to-date when emitted to`() {
        val firstTransformationFlow = MutableStateFlow(0)
        val secondTransformationFlow = MutableStateFlow(0)
        val thirdTransformationFlow = MutableStateFlow(0)
        val transformationFlow =
            merge(firstTransformationFlow, secondTransformationFlow, thirdTransformationFlow)
        val joinedFlow = listOf(1, 2, 3)
            .joinToFlow({ current, replacement -> current == 2 && replacement == 4 }) {
                transformationFlow
            }
        firstTransformationFlow.value = 2
        secondTransformationFlow.value = 4
        thirdTransformationFlow.value = 6
        runTest {
            assertEquals(listOf(4, 6), joinedFlow.first())
        }
    }
}