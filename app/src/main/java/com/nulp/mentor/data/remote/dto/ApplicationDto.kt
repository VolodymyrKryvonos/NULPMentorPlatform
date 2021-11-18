package com.nulp.mentor.data.remote.dto

import java.io.Serializable

data class ApplicationDto(
    val userId: Int,
    val mentorId: Int,
    val subjectId: Int,
    val date: Long,
    val comment: String,
    val id: Int,
    val state: Int
): Serializable