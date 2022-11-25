package com.jeanbarrossilva.ongoing.platform.registry

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jeanbarrossilva.ongoing.platform.registry.activity.ActivityDao
import com.jeanbarrossilva.ongoing.platform.registry.activity.ActivityEntity
import com.jeanbarrossilva.ongoing.platform.registry.observer.ObserverDao
import com.jeanbarrossilva.ongoing.platform.registry.observer.ObserverEntity
import com.jeanbarrossilva.ongoing.platform.registry.status.StatusDao
import com.jeanbarrossilva.ongoing.platform.registry.status.StatusEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

@Database(
    entities = [ActivityEntity::class, StatusEntity::class, ObserverEntity::class],
    version = 1
)
abstract class OngoingDatabase internal constructor(): RoomDatabase() {
    abstract val activityDao: ActivityDao
    abstract val statusDao: StatusDao
    abstract val observerDao: ObserverDao

    val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun close() {
        super.close()
        coroutineScope.cancel()
    }

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