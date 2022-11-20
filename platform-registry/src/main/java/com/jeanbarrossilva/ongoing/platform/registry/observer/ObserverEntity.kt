package com.jeanbarrossilva.ongoing.platform.registry.observer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "observers")
internal class ObserverEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "activity_id") val activityId: Long
) {
    constructor(userId: String, activityId: Long): this(id = 0, userId, activityId)
}