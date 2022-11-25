package com.jeanbarrossilva.ongoing.platform.registry.test

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.jeanbarrossilva.ongoing.platform.registry.OngoingDatabase
import com.jeanbarrossilva.ongoing.platform.registry.test.extensions.Statement
import org.junit.rules.ExternalResource
import org.junit.runner.Description
import org.junit.runners.model.Statement

class OngoingDatabaseRule: ExternalResource() {
    private val context
        get() = ApplicationProvider.getApplicationContext<Context>()

    private var database: OngoingDatabase? = null

    override fun apply(base: Statement, description: Description): Statement {
        return Statement {
            before()
            try { base.evaluate() } finally { }
            after()
        }
    }

    override fun before() {
        database = Room.inMemoryDatabaseBuilder(context, OngoingDatabase::class.java).build()
    }

    override fun after() {
        database?.close()
        database = null
    }

    fun getDatabase(): OngoingDatabase {
        return try {
            requireNotNull(database)
        } catch (_: NullPointerException) {
            throw IllegalStateException("Cannot get the database because the test's already done.")
        }
    }
}