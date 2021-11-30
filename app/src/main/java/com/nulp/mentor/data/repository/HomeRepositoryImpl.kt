package com.nulp.mentor.data.repository

import com.nulp.mentor.data.remote.Api
import com.nulp.mentor.data.remote.dto.BestMentorDto
import com.nulp.mentor.data.remote.dto.SubjectDto
import com.nulp.mentor.domain.repository.HomeRepository

class HomeRepositoryImpl(private val api: Api): HomeRepository {
    override suspend fun getBestMentors(): List<BestMentorDto> {
        return api.getBestMentors()
    }

    override suspend fun getSubjects(): List<SubjectDto> {
        return api.getSubjects()
    }
}