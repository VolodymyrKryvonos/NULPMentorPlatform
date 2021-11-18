package com.nulp.mentor.data.remote.request_body

import java.io.Serializable

data class SignupBody(
    val name: String,
    val surname: String,
    var password: String,
    val email: String,
    val course: Int,
    val specialty: String,
    val isMentor: Boolean,
    val token: String
) : Serializable
