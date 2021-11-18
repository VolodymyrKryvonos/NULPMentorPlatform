package com.nulp.mentor.domain.model

data class Subject(
    val id: Int,
    val name: String,
    val course: Int,
    var mentors: List<Mentor> = emptyList()
): java.io.Serializable
