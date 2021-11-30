package com.nulp.mentor.presentation.account

import com.nulp.mentor.domain.model.Mentor

data class MentorState(
    val isLoading: Boolean = false,
    val mentor: Mentor = Mentor(),
    val error: String = ""
)