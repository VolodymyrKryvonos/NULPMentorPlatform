package com.nulp.mentor.domain.repository

import com.nulp.mentor.data.remote.dto.BestMentorDto
import com.nulp.mentor.data.remote.dto.UserDto

interface HomeRepository{

    suspend fun getBestMentors(): List<BestMentorDto>

}