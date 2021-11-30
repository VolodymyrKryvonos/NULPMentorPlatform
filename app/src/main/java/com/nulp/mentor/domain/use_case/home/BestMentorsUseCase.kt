package com.nulp.mentor.domain.use_case.home

import com.nulp.mentor.common.Resource
import com.nulp.mentor.domain.model.BestMentor
import com.nulp.mentor.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BestMentorsUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    operator fun invoke(): Flow<Resource<List<BestMentor>>> = flow {
        try {
            emit(Resource.Loading<List<BestMentor>>())
            val mentors = homeRepository.getBestMentors()
                .map { BestMentor(mentor = it.mentor.toMentor(), subject = it.subject.toSubject()) }
            emit(Resource.Success<List<BestMentor>>(mentors))
        } catch (e: HttpException) {
            when (e.code()) {
                else -> {
                    emit(
                        Resource.Error<List<BestMentor>>(
                            e.localizedMessage ?: "Щось пішло не так"
                        )
                    )
                }
            }
        } catch (e: IOException) {
            emit(Resource.Error<List<BestMentor>>("Перевірте, будь ласка, Інтернет-з'єднання."))
        } catch (e: Exception) {
            emit(Resource.Error<List<BestMentor>>(e.localizedMessage ?: "Щось пішло не так"))
        }
    }

}