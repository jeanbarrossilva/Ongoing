package com.jeanbarrossilva.ongoing.platform.registry.observer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "observers")
internal class ObserverEntity(
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "activity_id") val activityId: Long
) {
    @PrimaryKey
    var id = "$userId@$activityId"
}