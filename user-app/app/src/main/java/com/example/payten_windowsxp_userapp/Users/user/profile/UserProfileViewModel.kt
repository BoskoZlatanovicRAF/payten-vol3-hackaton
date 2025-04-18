package com.example.payten_windowsxp_userapp.Users.user.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payten_windowsxp_userapp.Login.LoginScreenContract
import com.example.payten_windowsxp_userapp.Users.repository.UserRepository
import com.example.payten_windowsxp_userapp.Users.user.QR.GenerateQRContract
import com.example.payten_windowsxp_userapp.Users.user.profile.UserProfileContract.UserProfileState
import com.example.payten_windowsxp_userapp.auth.AuthData
import com.example.payten_windowsxp_userapp.auth.AuthStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val authStore: AuthStore,
    private val userRepository: UserRepository
) : ViewModel(){
    private val _state = MutableStateFlow(UserProfileState())
    val state = _state.asStateFlow()

    private fun setState(reducer: UserProfileState.() -> UserProfileState) = _state.update(reducer)

    private val events = MutableSharedFlow<UserProfileContract.UserProfileScreenUiEvent>()
    fun setEvent(event: UserProfileContract.UserProfileScreenUiEvent) =
        viewModelScope.launch { events.emit(event) }

    init {
        loadUserInfo()
        observeLogOutClick()
    }

    private fun observeLogOutClick() {
        viewModelScope.launch {
            events.filterIsInstance<UserProfileContract.UserProfileScreenUiEvent.logOutClick>()
                .collect{ event ->
                    authStore.clearAuthData()
                    setState { copy(loggedOut = true) }
                }

        }
    }

    private fun loadUserInfo(){
        viewModelScope.launch {
            setState { copy(loading = true) }
            val authData = authStore.authData.value
            if(authData.firstname.isNotEmpty()){
                val user = userRepository.getUserByID(authData.id.toInt())
                setState {
                    copy(loading = false,
                        firstName = user.firstname,
                        lastName = user.lastName,
                        email = user.email,
                        dateOfBirth = user.birthdate,
                        phoneNumber = user.phoneNumber,
                        password = user.password
                    )
                }
            } else {
                setState { copy(loading = false) }
            }
        }
    }
}