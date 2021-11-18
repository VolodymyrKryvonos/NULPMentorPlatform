package com.nulp.mentor.domain.use_case.auth

import com.nulp.mentor.common.Resource
import com.nulp.mentor.data.remote.dto.UserDto
import com.nulp.mentor.data.remote.request_body.SignupBody
import com.nulp.mentor.domain.model.User
import com.nulp.mentor.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SignupUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(signupBody: SignupBody): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading<User>())
            val user = repository.signup(signupBody)
            emit(Resource.Success<User>(user.toUser()))
        } catch(e: HttpException) {
            emit(Resource.Error<User>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<User>("Перевірте, будь ласка, Інтернет-з'єднання."))
        }
    }
}