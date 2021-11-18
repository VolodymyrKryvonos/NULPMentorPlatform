package com.nulp.mentor.data.remote.request_body

import java.io.Serializable

data class MentorReplyBody (
    val mentorId: Int,
    val requestId: Int,
    val comment: String,
    val date: Long
): Serializable