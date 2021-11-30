package com.nulp.mentor.data.local.daos

import androidx.room.*
import com.nulp.mentor.data.local.entities.NotificationEntity

@Dao
interface NotificationDao {

    @Query("SELECT * FROM Notifications")
    suspend fun getAllNotifications(): List<NotificationEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNotification(notification: NotificationEntity)

    @Delete
    suspend fun removeNotification(notification: NotificationEntity)
}