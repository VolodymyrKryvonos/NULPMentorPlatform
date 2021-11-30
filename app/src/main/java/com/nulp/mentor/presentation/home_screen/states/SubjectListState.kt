package com.nulp.mentor.presentation.home_screen.states

import com.nulp.mentor.domain.model.Subject

data class SubjectListState(
    val isLoading: Boolean = false,
    val subjects: List<Subject> = emptyList(),
    val error: String = ""
)
