package com.nulp.mentor.presentation.account

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nulp.mentor.common.PrefService
import com.nulp.mentor.common.Resource
import com.nulp.mentor.domain.model.Mentor
import com.nulp.mentor.domain.model.User
import com.nulp.mentor.domain.use_case.account.BecomeMentorUseCase
import com.nulp.mentor.domain.use_case.account.GetMentorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getMentorUseCase: GetMentorUseCase,
    private val becomeMentorUseCase: BecomeMentorUseCase,
    private val prefService: PrefService
) :
    ViewModel() {

    private val _mentorState = mutableStateOf(MentorState())
    val mentorState: State<MentorState> = _mentorState

    init {
        getMentor(prefService.getUser()?.id ?: -1)
    }

    private fun getMentor(mentorId: Int) {
        getMentorUseCase(mentorId).onEach {
            when (it) {
                is Resource.Error -> {
                    _mentorState.value = MentorState(error = it.message ?: "")
                }
                is Resource.Loading -> {
                    _mentorState.value = MentorState(isLoading = true)
                }
                is Resource.Success -> {
                    _mentorState.value = MentorState(mentor = it.data ?: Mentor())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun becomeMentor(mentorId: Int) {
        becomeMentorUseCase(mentorId).onEach {
            when (it) {
                is Resource.Error -> {
                    _mentorState.value = MentorState(error = it.message ?: "")
                }
                is Resource.Loading -> {
                    _mentorState.value = MentorState(isLoading = true)
                }
                is Resource.Success -> {
                    if (it.data != null) {
                        prefService.storeUser(
                            user = User(
                                id = it.data.id,
                                course = it.data.course,
                                isMentor = true,
                                name = it.data.name,
                                surname = it.data.surname,
                                email = it.data.email,
                                specialty = it.data.specialty,
                                registerDate = it.data.registerDate
                            )
                        )
                        _mentorState.value = MentorState(mentor = it.data)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}