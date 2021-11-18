package com.nulp.mentor.presentation.register_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nulp.mentor.common.PrefService
import com.nulp.mentor.common.Resource
import com.nulp.mentor.common.sha1Hash
import com.nulp.mentor.data.remote.request_body.SignupBody
import com.nulp.mentor.domain.model.User
import com.nulp.mentor.domain.use_case.auth.SignupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase,
    private val prefService: PrefService
) : ViewModel() {

    private val _userState = mutableStateOf<UserState>(UserState.None)
    val userState: State<UserState> = _userState

    fun signup(signupBody: SignupBody, confirmationPassword: String) {
        _userState.value = UserState.Loading

        viewModelScope.launch {
            if (!validate(signupBody, confirmationPassword)) {
                return@launch
            }
            signupBody.password = sha1Hash(signupBody.password)
            signupUseCase(signupBody).onEach {
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

    private suspend fun validate(signupBody: SignupBody, confirmationPassword: String): Boolean {
        delay(50)
        if (signupBody.email.isEmpty()) {
            _userState.value = (UserState.Error(error = "Електронна адреса не може бути порожньою"))
            return false
        }
        if (signupBody.name.isEmpty()) {
            _userState.value = (UserState.Error(error = "Ім'я не може бути порожнім"))
            return false
        }
        if (signupBody.email.isEmpty()) {
            _userState.value = (UserState.Error(error = "Фамілія не може бути порожньою"))
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(signupBody.email).matches()) {
            _userState.value = (UserState.Error(error = "Недійсна адреса електронної пошти"))
            return false
        }
        if (signupBody.password.length < 8) {
            _userState.value = (UserState.Error(error = "Пароль має містити не менше 8 символів"))
            return false
        }
        if (signupBody.password != confirmationPassword) {
            _userState.value = (UserState.Error(error = "Паролі не збігаються"))
            return false
        }
        if (signupBody.specialty.isEmpty()) {
            _userState.value = (UserState.Error(error = "Оберіть спеціальність"))
            return false
        }
        return true
    }

}