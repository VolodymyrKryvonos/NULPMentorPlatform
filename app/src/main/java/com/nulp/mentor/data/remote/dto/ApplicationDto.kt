package com.nulp.mentor.data.remote.dto

import com.nulp.mentor.domain.model.Application
import java.io.Serializable
import java.util.*

data class ApplicationDto(
    val userId: Int,
    val mentorId: Int,
    val subjectId: Int,
    val date: Long,
    val comment: String,
    val id: Int,
    val state: Int
) : Serializable

data class ApplicationData(
    val user: UserDto,
    val mentor: UserDto,
    val subject: SubjectDto,
    val date: Long,
    val comment: String,
    val id: Int,
    val state: Int
) : Serializable {

    fun toApplication() = Application(
        user = user.toUser(),
        mentor = mentor.toMentor(),
        subject = subject.toSubject(),
        date = Date(date),
        comment = comment,
        id = id
    )
}