package com.jeanbarrossilva.ongoing.platform.registry.activity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ActivityDao internal constructor() {
    @Query("SELECT * FROM activities")
    internal abstract fun selectAll(): Flow<List<ActivityEntity>>

    @Query("SELECT * FROM activities WHERE id = :id")
    internal abstract fun selectById(id: String): Flow<ActivityEntity?>

    @Insert
    internal abstract suspend fun insert(activity: ActivityEntity): Long

    @Query("UPDATE activities SET name = :name WHERE id = :id")
    internal abstract suspend fun updateName(id: String, name: String)

    @Query("UPDATE activities SET icon = :icon WHERE id = :id")
    internal abstract suspend fun updateIcon(id: String, icon: Icon)

    @Query("UPDATE activities SET current_status = :currentStatus WHERE id = :id")
    internal abstract suspend fun updateCurrentStatus(id: String, currentStatus: Status)

    @Query("DELETE FROM activities WHERE id = :id")
    internal abstract suspend fun delete(id: String)

    @Query("DELETE FROM activities")
    internal abstract suspend fun deleteAll()
}