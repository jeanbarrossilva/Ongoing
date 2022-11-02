package com.jeanbarrossilva.ongoing.platform.registry.status

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "statuses")
internal data class StatusEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "activity_id") val activityId: Long,
    val name: String
)