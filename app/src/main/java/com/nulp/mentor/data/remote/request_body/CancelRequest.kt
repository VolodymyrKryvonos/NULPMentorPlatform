package com.nulp.mentor.data.remote.request_body

import java.io.Serializable

data class CancelRequest(
    val requestId: Int,
    val date: Long
): Serializable
