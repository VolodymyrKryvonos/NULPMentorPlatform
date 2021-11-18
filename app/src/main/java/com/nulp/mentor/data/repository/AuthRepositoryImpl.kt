package com.nulp.mentor.data.repository

import com.nulp.mentor.data.remote.Api
import com.nulp.mentor.data.remote.dto.UserDto
import com.nulp.mentor.data.remote.request_body.LoginBody
import com.nulp.mentor.data.remote.request_body.SignupBody
import com.nulp.mentor.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: Api
): AuthRepository {
    override suspend fun login(pass: String, email: String, token: String): UserDto {
        return api.login(LoginBody(password = pass, email = email, token = token))
    }

    override suspend fun signup(signupBody: SignupBody): UserDto {
        return api.signup(signupBody)
    }

}