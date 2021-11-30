package com.nulp.mentor.data.repository

import com.nulp.mentor.data.local.daos.NotificationDao
import com.nulp.mentor.data.local.entities.NotificationEntity
import com.nulp.mentor.data.remote.Api
import com.nulp.mentor.domain.model.Application
import com.nulp.mentor.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val api: Api,
    private val dao: NotificationDao
) : NotificationRepository {
    override suspend fun getNotifications(): List<NotificationEntity> {
        return dao.getAllNotifications()
    }

    override fun addNotification(notification: NotificationEntity) {
        dao.addNotification(notification)
    }

    override suspend fun removeNotification(notification: NotificationEntity) {
        dao.removeNotification(notification)
    }

    override suspend fun getApplications(ids: List<Int>): List<Application> {
        return api.getApplications(ids).map { it.toApplication() }
    }
}