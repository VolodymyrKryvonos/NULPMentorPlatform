package com.nulp.mentor.data.remote.request_body

import java.io.Serializable

data class MentorRequestBody(
    val userId: Int,
    val comment: String,
    val subjectId: Int,
    val date: Long
) : Serializable
