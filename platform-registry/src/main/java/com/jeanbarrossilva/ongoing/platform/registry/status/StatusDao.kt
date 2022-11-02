package com.jeanbarrossilva.ongoing.platform.registry.status

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class StatusDao internal constructor() {
    @Query("SELECT * FROM statuses")
    internal abstract fun getStatuses(): Flow<List<StatusEntity>>

    @Query("SELECT * FROM statuses WHERE activity_id = :activityId")
    internal abstract fun getStatusesByActivityId(activityId: Long): Flow<List<StatusEntity>>

    @Insert
    internal abstract suspend fun insert(status: StatusEntity)

    @Query("DELETE FROM statuses WHERE activity_id = :activityId")
    internal abstract suspend fun deleteByActivityId(activityId: Long)

    @Query("DELETE FROM statuses")
    internal abstract suspend fun deleteAll()
}