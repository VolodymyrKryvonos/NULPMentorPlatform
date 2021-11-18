package com.nulp.mentor.data.remote.request_body

import java.io.Serializable

data class MakeApplicationBody(
    val userId: Int,
    val mentorId: Int,
    val subjectId: Int,
    val date: Long,
    val comment: String
): Serializable
