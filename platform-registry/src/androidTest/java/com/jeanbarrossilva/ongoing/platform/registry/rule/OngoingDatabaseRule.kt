package com.jeanbarrossilva.ongoing.platform.registry.rule

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.jeanbarrossilva.ongoing.platform.registry.OngoingDatabase
import org.junit.rules.ExternalResource

internal class OngoingDatabaseRule: ExternalResource() {
    private val context
        get() = ApplicationProvider.getApplicationContext<Context>()

    lateinit var database: OngoingDatabase
        private set

    override fun before() {
        database = Room.inMemoryDatabaseBuilder(context, OngoingDatabase::class.java).build()
    }

    override fun after() {
        database.close()
    }
}