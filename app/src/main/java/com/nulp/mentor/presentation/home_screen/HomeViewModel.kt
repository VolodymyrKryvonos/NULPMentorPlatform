package com.nulp.mentor.presentation.home_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nulp.mentor.common.PrefService
import com.nulp.mentor.common.Resource
import com.nulp.mentor.domain.use_case.home.BestMentorsUseCase
import com.nulp.mentor.domain.use_case.home.NotificationsUseCase
import com.nulp.mentor.domain.use_case.home.SubjectsUseCase
import com.nulp.mentor.presentation.home_screen.states.BestMentorsListState
import com.nulp.mentor.presentation.home_screen.states.NotificationListState
import com.nulp.mentor.presentation.home_screen.states.SubjectListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bestMentorsUseCase: BestMentorsUseCase,
    private val subjectsUseCase: SubjectsUseCase,
    private val notificationsUseCase: NotificationsUseCase,
    private val prefService: PrefService
) :
    ViewModel() {
    private val _mentorsState = mutableStateOf(BestMentorsListState())
    val mentorsState: State<BestMentorsListState> = _mentorsState

    private val _subjectsState = mutableStateOf(SubjectListState())
    val subjectsState: State<SubjectListState> = _subjectsState

    private val _notificationsState = mutableStateOf(NotificationListState())
    val notificationsState: State<NotificationListState> = _notificationsState

    init {
        getBestMentors()
        getSubjects(
            prefService.getUser()?.specialty ?: "",
            course = prefService.getUser()?.course ?: -1
        )
        getNotifications()
    }

    private fun getNotifications() {
        notificationsUseCase().onEach {
            Log.e("getNotifications", it.data.toString())
            when (it) {
                is Resource.Error -> {
                    _notificationsState.value = NotificationListState(error = it.message ?: "")
                }
                is Resource.Loading -> {
                    _notificationsState.value = NotificationListState(isLoading = true)
                }
                is Resource.Success -> {
                    _notificationsState.value =
                        NotificationListState(
                            notifications = it.data
                                ?: emptyList()
                        )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getSubjects(specialty: String = "", course: Int = -1) {
        Log.e("getSubjects", "call")
        subjectsUseCase().onEach {
            Log.e("getSubjects", it.data.toString())
            when (it) {
                is Resource.Error -> {
                    _subjectsState.value = SubjectListState(error = it.message ?: "")
                }
                is Resource.Loading -> {
                    _subjectsState.value = SubjectListState(isLoading = true)
                }
                is Resource.Success -> {
                    _subjectsState.value =
                        SubjectListState(
                            subjects = it.data
                                ?: emptyList()
                        )
                }
            }
        }.launchIn(viewModelScope)
    }//?.filter { subject -> (course == -1 || course == subject.course) }

    private fun getBestMentors() {
        Log.e("getBestMentors", "call")
        bestMentorsUseCase().onEach {
            Log.e("bestMentorsUseCase", it.toString())
            when (it) {
                is Resource.Error -> {
                    _mentorsState.value = BestMentorsListState(error = it.message ?: "")
                }
                is Resource.Loading -> {
                    _mentorsState.value = BestMentorsListState(isLoading = true)
                }
                is Resource.Success -> {
                    _mentorsState.value = BestMentorsListState(mentors = it.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }
}