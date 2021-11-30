package com.nulp.mentor.data.repository

import com.nulp.mentor.data.remote.Api
import com.nulp.mentor.data.remote.dto.SubjectDto
import com.nulp.mentor.data.remote.request_body.SubscribeOnSubjectBody
import com.nulp.mentor.domain.repository.SubjectRepository
import retrofit2.Response
import javax.inject.Inject

class SubjectRepositoryImpl @Inject constructor(private val api: Api) : SubjectRepository {
    override suspend fun getSubjects(): List<SubjectDto> {
        return api.getSubjects()
    }

    override suspend fun subscribeOnSubject(subscribeOnSubjectBody: SubscribeOnSubjectBody): Response<Void> {
        return api.subscribeOnSubject(subscribeOnSubjectBody)
    }
}