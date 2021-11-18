package com.nulp.mentor.data.remote.request_body

import java.io.Serializable

data class LoginBody(
    val password: String,
    val email: String,
    val token: String
): Serializable
