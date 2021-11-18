package com.nulp.mentor.data.remote.request_body

import java.io.Serializable

data class RateBody(
    val rate: Int,
    val userId: Int,
    val mentorId: Int
) : Serializable
