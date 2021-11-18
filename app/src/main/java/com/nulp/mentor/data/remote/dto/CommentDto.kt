package com.nulp.mentor.data.remote.dto

import java.io.Serializable

data class CommentDto(
    val id: Int,
    val comment: String="",
    val user :String = "",
    val date : Long
): Serializable
