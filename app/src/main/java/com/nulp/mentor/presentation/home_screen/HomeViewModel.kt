package com.nulp.mentor.presentation.home_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nulp.mentor.common.Resource
import com.nulp.mentor.domain.use_case.home.BestMentorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val bestMentorsUseCase: BestMentorsUseCase) :
    ViewModel() {
    private val _mentorsState = mutableStateOf(BestMentorsListState())
    val mentorsState: State<BestMentorsListState> = _mentorsState

    init {
        getBestMentors()
    }

    private fun getBestMentors() {
            Log.e("getBestMentors", "call")
            bestMentorsUseCase().onEach {
                Log.e("bestMentorsUseCase", it.toString())
                when (it) {
                    is Resource.Error -> {
                        _mentorsState.value = BestMentorsListState(error = it.message?:"")
                    }
                    is Resource.Loading -> {
                        _mentorsState.value = BestMentorsListState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _mentorsState.value = BestMentorsListState(mentors = it.data?: emptyList())
                    }
                }
            }.launchIn(viewModelScope)
    }
}