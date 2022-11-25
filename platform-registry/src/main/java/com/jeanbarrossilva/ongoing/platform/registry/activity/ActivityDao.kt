package com.jeanbarrossilva.ongoing.platform.registry.activity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ActivityDao internal constructor() {
    @Query("SELECT * FROM activities")
    internal abstract fun selectAll(): Flow<List<ActivityEntity>>

    @Query("SELECT * FROM activities WHERE id = :id")
    internal abstract fun selectById(id: String): Flow<ActivityEntity?>

    @Query("SELECT owner_user_id FROM activities WHERE id = :activityId")
    internal abstract suspend fun selectOwnerUserId(activityId: String): String

    @Query("SELECT name FROM activities WHERE id = :activityId")
    internal abstract suspend fun selectName(activityId: String): String

    @Insert
    internal abstract suspend fun insert(activity: ActivityEntity): Long

    @Query("UPDATE activities SET owner_user_id = :ownerUserId WHERE id = :id")
    internal abstract suspend fun updateOwnerUserId(id: String, ownerUserId: String?)

    @Query("UPDATE activities SET name = :name WHERE id = :id")
    internal abstract suspend fun updateName(id: String, name: String)

    @Query("UPDATE activities SET icon = :icon WHERE id = :id")
    internal abstract suspend fun updateIcon(id: String, icon: Icon)

    @Query("DELETE FROM activities WHERE id = :id")
    internal abstract suspend fun delete(id: String)

    @Query("DELETE FROM activities")
    internal abstract suspend fun deleteAll()
}