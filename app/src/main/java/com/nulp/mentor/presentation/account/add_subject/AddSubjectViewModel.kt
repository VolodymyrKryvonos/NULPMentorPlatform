package com.nulp.mentor.presentation.account.add_subject

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nulp.mentor.common.PrefService
import com.nulp.mentor.common.Resource
import com.nulp.mentor.domain.model.Subject
import com.nulp.mentor.domain.use_case.account.SubscribeOnSubjectUseCase
import com.nulp.mentor.domain.use_case.home.SubjectsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddSubjectViewModel @Inject constructor(
    private val subscribeOnSubjectUseCase: SubscribeOnSubjectUseCase,
    private val getSubjectUseCase: SubjectsUseCase,
    private val prefService: PrefService
) : ViewModel() {

    private val _subjectsListState = mutableStateOf(SubjectListState())
    val subjectsListState: State<SubjectListState> = _subjectsListState

    private val _subscribeState = mutableStateOf(SubscribeState())
    val subscribeState: State<SubscribeState> = _subscribeState

    private var subjects = emptyList<Subject>()

    init {
        getSubjects()
    }

    fun selectByCourse(course: Int) {
        _subjectsListState.value =
            SubjectListState(subjectList = subjects.filter { it.course == course })
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

    fun subscribeOnSubject(subjectId: Int) {
        subscribeOnSubjectUseCase(
            mentorId = prefService.getUser()?.id ?: -1,
            subjectId = subjectId
        ).onEach {
            when (it) {
                is Resource.Error -> {
                    _subscribeState.value = SubscribeState(error = it.message ?: "")
                }
                is Resource.Loading -> {
                    _subscribeState.value = SubscribeState(isLoading = true)
                }
                is Resource.Success -> {
                    _subscribeState.value = SubscribeState(isSuccess = it.data ?: false)
                }
            }
        }.launchIn(viewModelScope)
    }

}