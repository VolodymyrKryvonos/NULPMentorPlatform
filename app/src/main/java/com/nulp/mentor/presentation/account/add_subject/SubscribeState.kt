package com.nulp.mentor.presentation.account.add_subject

data class SubscribeState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String = ""
)