package com.nulp.mentor.domain.repository

import com.nulp.mentor.data.remote.dto.BestMentorDto
import com.nulp.mentor.data.remote.dto.SubjectDto

interface HomeRepository {

    suspend fun getBestMentors(): List<BestMentorDto>

    suspend fun getSubjects(): List<SubjectDto>

}