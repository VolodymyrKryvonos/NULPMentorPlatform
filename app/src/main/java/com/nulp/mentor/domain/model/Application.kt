package com.nulp.mentor.domain.model

import java.io.Serializable
import java.util.*

data class Application(
    val user: User,
    val mentor: Mentor,
    val subject: Subject,
    val date: Date,
    val comment: String,
    val id: Int
) : Serializable