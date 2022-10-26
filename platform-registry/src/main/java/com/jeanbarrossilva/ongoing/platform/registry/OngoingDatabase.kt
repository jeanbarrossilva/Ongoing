package com.jeanbarrossilva.ongoing.platform.registry

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jeanbarrossilva.ongoing.platform.registry.activity.ActivityDao
import com.jeanbarrossilva.ongoing.platform.registry.activity.ActivityEntity

@Database(entities = [ActivityEntity::class], version = 1)
abstract class OngoingDatabase internal constructor() : RoomDatabase() {
    abstract val activityDao: ActivityDao

    companion object {
        private lateinit var instance: OngoingDatabase

        internal fun getInstance(context: Context): OngoingDatabase {
            return if (this::instance.isInitialized) {
                instance
            } else {
                instance = createInstance(context)
                instance
            }
        }

        private fun createInstance(context: Context): OngoingDatabase {
            return Room.databaseBuilder(context, OngoingDatabase::class.java, "ongoing-db").build()
        }
    }
}