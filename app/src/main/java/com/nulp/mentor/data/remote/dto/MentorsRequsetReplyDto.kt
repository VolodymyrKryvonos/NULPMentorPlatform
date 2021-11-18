package com.nulp.mentor.data.remote.dto

import java.io.Serializable

data class MentorsRequestReply(
    val pushMessage: String,
    val token: String,
    val requestId: Int,
    val comment: String,
    val mentorsEmail: String
): Serializable
