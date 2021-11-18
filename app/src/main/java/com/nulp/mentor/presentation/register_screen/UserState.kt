package com.nulp.mentor.presentation.register_screen

import com.nulp.mentor.domain.model.User

sealed class UserState{
    object Loading : UserState()
    class Error(val error: String): UserState()
    object None : UserState()
    class Success(val user: User): UserState()
}
