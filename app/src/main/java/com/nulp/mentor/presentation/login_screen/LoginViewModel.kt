package com.nulp.mentor.presentation.login_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nulp.mentor.common.PrefService
import com.nulp.mentor.common.Resource
import com.nulp.mentor.common.sha1Hash
import com.nulp.mentor.domain.model.User
import com.nulp.mentor.domain.use_case.auth.LoginUseCase
import com.nulp.mentor.presentation.register_screen.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val prefService: PrefService
) : ViewModel() {

    var email = mutableStateOf("")
    var password = mutableStateOf("")

    private val _userState = mutableStateOf<UserState>(UserState.None)
    val userState: State<UserState> = _userState

    fun login(token: String) {
        loginUseCase(sha1Hash(password.value), email.value, token).onEach {
            when (it) {
                is Resource.Success -> {
                    prefService.storeUser(it.data?:User())
                    _userState.value = UserState.Success(it.data ?: User())
                }
                is Resource.Loading -> {
                    _userState.value = UserState.Loading
                }
                is Resource.Error -> {
                    _userState.value = UserState.Error(error = it.message ?: "")
                }
            }
        }.launchIn(viewModelScope)
    }

}