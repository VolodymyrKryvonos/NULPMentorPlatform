package com.nulp.mentor.domain.model

import android.app.Application
import com.nulp.mentor.data.remote.dto.CommentDto
import com.nulp.mentor.data.remote.dto.RequestDto
import com.nulp.mentor.data.remote.dto.SubjectDto
import com.nulp.mentor.data.remote.dto.UserDto

data class User(
    val id: Int = -1,
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val course: Int = -1,
    val specialty: String = "",
    val isMentor: Boolean = false,
    val registerDate: Long = System.currentTimeMillis(),
    val userApplications: List<Application> = emptyList(),
    val requests: List<RequestDto> = emptyList(),
)

data class Mentor(
    val id: Int = -1,
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val course: Int = -1,
    val specialty: String = "",
    val registerDate: Long = System.currentTimeMillis(),
    var mentees: List<User> = emptyList(),
    var subjects: List<SubjectDto> = emptyList(),
    val appeals: List<Application> = emptyList(),
    val comments: List<CommentDto> = emptyList(),
    val rate: Float = 0F,
)