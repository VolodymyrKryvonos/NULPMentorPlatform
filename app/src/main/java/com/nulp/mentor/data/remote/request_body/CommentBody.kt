package com.nulp.mentor.data.remote.request_body

import java.io.Serializable

data class CommentBody(
    val comment: String,
    val userId: Int,
    val mentorId: Int,
    val date: Long
) : Serializable
