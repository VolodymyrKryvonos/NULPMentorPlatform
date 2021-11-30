package com.nulp.mentor.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notifications")
data class NotificationEntity(
    @PrimaryKey
    val id: Long,
    val applicationId: Int,
    val message: String,
    val isConfirmed: Boolean?
)