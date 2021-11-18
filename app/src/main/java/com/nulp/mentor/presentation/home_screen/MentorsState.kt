package com.nulp.mentor.presentation.home_screen

import com.nulp.mentor.domain.model.BestMentor

data class BestMentorsListState (
    val isLoading: Boolean = false,
    val mentors: List<BestMentor> = emptyList(),
    val error: String = ""
)