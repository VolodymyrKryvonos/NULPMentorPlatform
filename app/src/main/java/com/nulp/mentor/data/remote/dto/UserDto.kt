package com.nulp.mentor.data.remote.dto

import android.app.Application
import com.nulp.mentor.domain.model.Mentor
import com.nulp.mentor.domain.model.User
import java.io.Serializable

data class UserDto(
    val id: Int = -1,
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val course: Int = -1,
    val specialty: String = "",
    val isMentor: Boolean = false,
    val registerDate: Long = System.currentTimeMillis(),
    val rate: Float = 0F,
    val token: String? = null,
    val comments: List<CommentDto> = emptyList(),
    val userApplications: List<Application> = emptyList(),
    val appeals: List<Application> = emptyList(),
    val requests: List<RequestDto> = emptyList(),
    var mentees: List<UserDto> = emptyList(),
    var subjects: List<SubjectDto> = emptyList()
) : Serializable {
    fun toUser() = User(
        id = id,
        name = name,
        surname = surname,
        email = email,
        course = course,
        specialty = specialty,
        registerDate = registerDate,
        isMentor = isMentor,
        userApplications = userApplications,
        requests = requests
    )

    fun toMentor(): Mentor {
        return Mentor(
            id = id,
            name = name,
            surname = surname,
            email = email,
            course = course,
            specialty = specialty,
            registerDate = registerDate,
            rate = rate,
            appeals = appeals,
            subjects = subjects,
            mentees = mentees.map { toUser() },
            comments = comments,
        )
    }
}
