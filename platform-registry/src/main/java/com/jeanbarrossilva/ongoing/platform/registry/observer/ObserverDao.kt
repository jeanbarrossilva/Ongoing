package com.jeanbarrossilva.ongoing.platform.registry.observer

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ObserverDao internal constructor() {
    @Query("SELECT * FROM observers")
    internal abstract fun selectAll(): Flow<List<ObserverEntity>>

    @Query("SELECT * FROM observers WHERE activity_id = :activityId")
    internal abstract fun selectByActivityId(activityId: Long): Flow<List<ObserverEntity>>

    @Insert
    internal abstract suspend fun insert(observer: ObserverEntity)

    @Delete
    internal abstract suspend fun delete(observer: ObserverEntity)

    @Query("DELETE FROM observers")
    internal abstract suspend fun deleteAll()
}