package com.nulp.mentor.domain.repository

import com.nulp.mentor.data.local.entities.NotificationEntity
import com.nulp.mentor.domain.model.Application

interface NotificationRepository {

    suspend fun getNotifications(): List<NotificationEntity>

    fun addNotification(notification: NotificationEntity)

    suspend fun removeNotification(notification: NotificationEntity)

    suspend fun getApplications(ids: List<Int>): List<Application>

}