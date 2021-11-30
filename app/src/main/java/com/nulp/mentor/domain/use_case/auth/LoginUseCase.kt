package com.nulp.mentor.domain.use_case.auth

import com.nulp.mentor.common.Resource
import com.nulp.mentor.domain.model.User
import com.nulp.mentor.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(pass: String, email: String, token: String): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading<User>())
            val user = repository.login(pass, email, token)
            emit(Resource.Success<User>(user.toUser()))
        } catch (e: HttpException) {
            when (e.code()) {
                403 -> {
                    emit(Resource.Error<User>("Невірно введений емейл або пароль!"))
                }
                else -> {
                    emit(Resource.Error<User>(e.localizedMessage ?: "Щось пішло не так"))
                }
            }
        } catch (e: IOException) {
            emit(Resource.Error<User>("Перевірте, будь ласка, Інтернет-з'єднання."))
        } catch (e: Exception) {
            emit(Resource.Error<User>(e.localizedMessage ?: "Щось пішло не так"))
        }
    }
}