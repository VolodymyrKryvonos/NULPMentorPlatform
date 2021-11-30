package com.nulp.mentor.domain.use_case.account

import com.nulp.mentor.common.Resource
import com.nulp.mentor.data.remote.request_body.SubscribeOnSubjectBody
import com.nulp.mentor.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SubscribeOnSubjectUseCase @Inject constructor(private val subjectRepository: SubjectRepository) {

    operator fun invoke(mentorId: Int, subjectId: Int): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading<Boolean>())
        val response = subjectRepository.subscribeOnSubject(
            SubscribeOnSubjectBody(
                mentorId = mentorId,
                subjectId = subjectId
            )
        )
        if (response.isSuccessful) {
            emit(Resource.Success<Boolean>(true))
        } else {
            emit(Resource.Error<Boolean>("Щось пішло не так, спробуйте пізніше"))
        }
    }
}