package com.nulp.mentor.presentation.notifications_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nulp.mentor.common.Resource
import com.nulp.mentor.domain.use_case.home.NotificationsUseCase
import com.nulp.mentor.presentation.home_screen.states.NotificationListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationsUseCase: NotificationsUseCase
) :
    ViewModel() {

    private val _notificationsState = mutableStateOf(NotificationListState())
    val notificationsState: State<NotificationListState> = _notificationsState

    init {
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
}