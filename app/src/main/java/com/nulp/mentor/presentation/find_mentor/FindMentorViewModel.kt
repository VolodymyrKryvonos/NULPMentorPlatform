package com.nulp.mentor.presentation.find_mentor

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nulp.mentor.common.Resource
import com.nulp.mentor.domain.model.Subject
import com.nulp.mentor.domain.use_case.home.SubjectsUseCase
import com.nulp.mentor.presentation.account.add_subject.SubjectListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FindMentorViewModel @Inject constructor(
    private val getSubjectUseCase: SubjectsUseCase
) : ViewModel() {

    private val _subjectsListState = mutableStateOf(SubjectListState())
    val subjectsListState: State<SubjectListState> = _subjectsListState
    private var subjects = emptyList<Subject>()

    init {
        getSubjects()
    }

    fun findByName(name: String) {

        _subjectsListState.value =
            SubjectListState(
                subjectList = if (name.isEmpty()) {
                    subjects
                } else subjects.filter { it.name.contains(name, true) })
        name.lowercase()
    }

    private fun getSubjects() {
        getSubjectUseCase().onEach {
            when (it) {

                is Resource.Error -> {
                    _subjectsListState.value = SubjectListState(error = it.message ?: "")
                }
                is Resource.Loading -> {
                    _subjectsListState.value = SubjectListState(isLoading = true)
                }
                is Resource.Success -> {
                    subjects = it.data ?: emptyList()
                    _subjectsListState.value =
                        SubjectListState(subjectList = it.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

}