package com.nulp.mentor.domain.use_case.account

import com.nulp.mentor.common.Resource
import com.nulp.mentor.domain.model.Mentor
import com.nulp.mentor.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BecomeMentorUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    operator fun invoke(mentorId: Int): Flow<Resource<Mentor>> = flow {
        try {
            emit(Resource.Loading<Mentor>())
            val mentor = repository.becomeMentor(mentorId)
            emit(Resource.Success<Mentor>(mentor))
        } catch (e: HttpException) {
            when (e.code()) {
                else -> {
                    emit(Resource.Error<Mentor>(e.localizedMessage ?: "Щось пішло не так"))
                }
            }
        } catch (e: IOException) {
            emit(Resource.Error<Mentor>("Перевірте, будь ласка, Інтернет-з'єднання."))
        } catch (e: Exception) {
            emit(Resource.Error<Mentor>(e.localizedMessage ?: "Щось пішло не так"))
        }
    }
}