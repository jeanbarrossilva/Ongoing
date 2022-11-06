package com.jeanbarrossilva.ongoing.platform.registry.activity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon

@Entity(tableName = "activities")
internal data class ActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "owner_user_id") val ownerUserId: String?,
    val name: String,
    val icon: Icon
)