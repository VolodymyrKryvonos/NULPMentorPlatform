package com.nulp.mentor.presentation.home_screen.states

import com.nulp.mentor.domain.model.Notification

data class NotificationListState(
    val isLoading: Boolean = false,
    val notifications: List<Notification> = emptyList(),
    val error: String = ""
)