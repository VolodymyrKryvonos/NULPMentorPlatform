package com.nulp.mentor.data.remote.dto

data class MentorsReplyDto(
    val pushMessage: String,
    val token: String,
    val applicationId: Int,
    val comment: String,
    val mentorsEmail: String,
    val accepted: Boolean
):java.io.Serializable
