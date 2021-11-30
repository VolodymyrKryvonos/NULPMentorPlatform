package com.nulp.mentor.domain.use_case.home

import com.nulp.mentor.common.Resource
import com.nulp.mentor.domain.model.Subject
import com.nulp.mentor.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SubjectsUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    operator fun invoke(): Flow<Resource<List<Subject>>> = flow {
        try {
            emit(Resource.Loading<List<Subject>>())
            val subjects = homeRepository.getSubjects().map { it.toSubjectWithMentors() }
            emit(Resource.Success<List<Subject>>(subjects))
        } catch (e: HttpException) {
            when (e.code()) {
                else -> {
                    emit(Resource.Error<List<Subject>>(e.localizedMessage ?: "Щось пішло не так"))
                }
            }
        } catch (e: IOException) {
            emit(Resource.Error<List<Subject>>("Перевірте, будь ласка, Інтернет-з'єднання."))
        } catch (e: Exception) {
            emit(Resource.Error<List<Subject>>(e.localizedMessage ?: "Щось пішло не так"))
        }
    }

}