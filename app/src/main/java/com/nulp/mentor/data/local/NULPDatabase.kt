package com.nulp.mentor.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nulp.mentor.data.local.daos.NotificationDao
import com.nulp.mentor.data.local.entities.NotificationEntity

@Database(
    entities = [NotificationEntity::class],
    version = 1
)
abstract class NULPDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao
}