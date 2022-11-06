package com.jeanbarrossilva.ongoing.platform.extensions

import android.content.Context
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.edit
import androidx.test.core.app.ApplicationProvider
import com.jeanbarrossilva.ongoing.platform.extensions.identification.CurrentVersionCodeProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTestRule

@OptIn(ExperimentalCoroutinesApi::class)
internal class ContextExtensionsTests {
    private val module = module {
        single {
            CurrentVersionCodeProvider {
                0
            }
        }
    }

    private val context
        get() = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule
    val koinRule = KoinTestRule.create { modules(module) }

    @Before
    fun setUp() {
        clearPreferences()
    }

    @After
    fun tearDown() {
        clearPreferences()
    }

    @Test
    fun onFirstRun() {
        var isFirstRun = false
        runTest {
            context.onFirstRun { isFirstRun = true }
            context.onFirstRun { isFirstRun = false }
        }
        assertTrue(isFirstRun)
    }

    private fun clearPreferences() {
        runTest {
            context.dataStore.edit(MutablePreferences::clear)
        }
    }
}