package com.nulp.mentor.data.remote.request_body

data class ReplyOnApplicationBody(
    val applicationId: Int,
    val comment: String,
    val accepted: Boolean
)
