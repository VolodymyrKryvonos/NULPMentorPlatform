package com.nulp.mentor.domain.repository

import com.nulp.mentor.data.remote.dto.SubjectDto
import com.nulp.mentor.data.remote.request_body.SubscribeOnSubjectBody
import retrofit2.Response

interface SubjectRepository {

    suspend fun getSubjects(): List<SubjectDto>

    suspend fun subscribeOnSubject(subscribeOnSubjectBody: SubscribeOnSubjectBody): Response<Void>

}