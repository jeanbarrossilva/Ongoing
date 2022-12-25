package com.jeanbarrossilva.ongoing.core.registry

import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.core.registry.inmemory.InMemoryActivityRecorder
import com.jeanbarrossilva.ongoing.core.registry.inmemory.InMemoryActivityRegistry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
internal class ActivityRecorderTests {
    private val registry = InMemoryActivityRegistry()
    private val recorder = InMemoryActivityRecorder(registry)
    private lateinit var activityId: String

    @BeforeTest
    fun setUp() {
        runTest {
            activityId = registry.register(ownerUserId = uuid(), name = "Fly to the Bahamas")
        }
    }

    @Test
    fun `GIVEN the ID of a non-owner user WHEN recording a name THEN it throws`() {
        runTest {
            assertFailsWith<IllegalArgumentException> {
                recorder.name(userId = uuid(), activityId, "Fly to Rio de Janeiro")
            }
        }
    }

    @Test
    fun `GIVEN the ID of a non-owner user WHEN recording an icon THEN it throws`() {
        runTest {
            assertFailsWith<IllegalArgumentException> {
                recorder.icon(userId = uuid(), activityId, Icon.BOOK)
            }
        }
    }

    @Test
    fun `GIVEN the ID of a non-owner user WHEN recording a status THEN it throws`() {
        runTest {
            assertFailsWith<IllegalArgumentException> {
                recorder.status(userId = uuid(), activityId, Status.ONGOING)
            }
        }
    }
}