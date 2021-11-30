package com.nulp.mentor.presentation.account.add_subject

import com.nulp.mentor.domain.model.Subject

data class SubjectListState(
    val isLoading: Boolean = false,
    val subjectList: List<Subject> = emptyList(),
    val error: String = ""
)