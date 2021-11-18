package com.nulp.mentor.data.remote.dto

import java.io.Serializable

data class RequestDto(
    val id: Int,
    val userId: Int,
    val date: Long,
    val subjectId: Int,
    val comment: String,
    val state: Int
): Serializable