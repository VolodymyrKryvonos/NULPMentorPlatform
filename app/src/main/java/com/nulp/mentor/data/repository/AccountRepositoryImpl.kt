package com.nulp.mentor.data.repository

import com.nulp.mentor.data.remote.Api
import com.nulp.mentor.domain.model.Mentor
import com.nulp.mentor.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(private val api: Api) : AccountRepository {
    override suspend fun getMentor(mentorId: Int): Mentor {
        return api.getMentor(mentorId).toMentor()
    }

    override suspend fun becomeMentor(mentorId: Int): Mentor {
        return api.becomeMentor(mentorId).toMentor()
    }
}