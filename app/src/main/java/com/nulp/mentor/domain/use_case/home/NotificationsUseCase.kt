package com.nulp.mentor.domain.use_case.home

import com.nulp.mentor.common.Resource
import com.nulp.mentor.domain.model.Notification
import com.nulp.mentor.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NotificationsUseCase @Inject constructor(private val notificationRepository: NotificationRepository) {

    operator fun invoke(): Flow<Resource<List<Notification>>> = flow {
        try {
            emit(Resource.Loading<List<Notification>>())
            val notifications = arrayListOf<Notification>()
            val notificationEntities = notificationRepository.getNotifications()
            val ids = notificationEntities.map { notification -> notification.applicationId }
            notificationRepository.getApplications(ids).forEachIndexed { index, application ->
                notifications.add(
                    Notification(
                        id = notificationEntities[index].id,
                        mentee = application.user,
                        mentor = application.mentor,
                        subject = application.subject,
                        applicationId = application.id,
                        message = notificationEntities[index].message,
                        isConfirmed = notificationEntities[index].isConfirmed
                    )
                )
            }
            emit(Resource.Success<List<Notification>>(notifications))
        } catch (e: HttpException) {
            when (e.code()) {
                else -> {
                    emit(
                        Resource.Error<List<Notification>>(
                            "Щось пішло не так"
                        )
                    )
                }
            }
        } catch (e: IOException) {
            emit(Resource.Error<List<Notification>>("Перевірте, будь ласка, Інтернет-з'єднання."))
        } catch (e: Exception) {
            emit(Resource.Error<List<Notification>>("Щось пішло не так"))
        }
    }

}