package com.nulp.mentor.domain.model

data class Notification(
    val id: Long,
    val mentee: User,
    val mentor: Mentor,
    val subject: Subject,
    val applicationId: Int,
    val message: String,
    val isConfirmed: Boolean?
)