package com.nulp.mentor.data.remote.dto

import com.nulp.mentor.domain.model.Subject


data class SubjectDto(
    val id: Int,
    val name: String,
    val course: Int,
    var mentors: List<UserDto> = emptyList()
): java.io.Serializable{
    fun toSubject() = Subject(
        id = id,
        name = name,
        course = course
    )

    fun toSubjectWithMentors() = Subject(
        id = id,
        name = name,
        course = course,
        mentors = mentors.map { it.toMentor() }
    )
}