package com.nulp.mentor.data.remote.dto

import com.nulp.mentor.data.remote.dto.SubjectDto
import com.nulp.mentor.data.remote.dto.UserDto
import java.io.Serializable

data class BestMentorDto(
    val mentor: UserDto,
    val subject: SubjectDto
): Serializable
