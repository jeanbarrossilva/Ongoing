package com.jeanbarrossilva.ongoing.platform.loadable

import app.cash.turbine.test
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.loadableFlowOf
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.replaceBy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

internal class FlowExtensionsTests {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN a flow WHEN replacing an emitted loadable THEN it is replaced`() {
        val replacement = Loadable.Loaded("Hello, world!")
        var emitted: Loadable<String>? = null
        runTest {
            loadableFlowOf<String>().replaceBy({ replacement }) { it is Loadable.Loading }.test {
                emitted = awaitItem()
            }
        }
        assertEquals(replacement, emitted)
    }
}