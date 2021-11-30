package com.nulp.mentor.domain.repository

import com.nulp.mentor.domain.model.Mentor

interface AccountRepository {

    suspend fun getMentor(mentorId: Int): Mentor

    suspend fun becomeMentor(mentorId: Int): Mentor
}