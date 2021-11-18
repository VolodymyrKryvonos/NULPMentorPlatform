package com.nulp.mentor.domain.repository

import com.nulp.mentor.data.remote.dto.UserDto
import com.nulp.mentor.data.remote.request_body.SignupBody

interface AuthRepository {

    suspend fun login(pass: String, email: String, token: String): UserDto

    suspend fun signup(signupBody: SignupBody): UserDto

}