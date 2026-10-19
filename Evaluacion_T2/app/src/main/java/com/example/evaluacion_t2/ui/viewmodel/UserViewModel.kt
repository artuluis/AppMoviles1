package com.example.evaluacion_t2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.evaluacion_t2.data.local.UserEntity
import com.example.evaluacion_t2.data.remote.ApiUser
import com.example.evaluacion_t2.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    val localUsers: StateFlow<List<UserEntity>> = repository.localUsers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _apiUsers = MutableStateFlow<List<ApiUser>>(emptyList())
    val apiUsers: StateFlow<List<ApiUser>> = _apiUsers.asStateFlow()

    private val _isLoadingApi = MutableStateFlow(false)
    val isLoadingApi: StateFlow<Boolean> = _isLoadingApi.asStateFlow()

    private val _apiError = MutableStateFlow<String?>(null)
    val apiError: StateFlow<String?> = _apiError.asStateFlow()

    private val _selectedUser = MutableStateFlow<UserEntity?>(null)
    val selectedUser: StateFlow<UserEntity?> = _selectedUser.asStateFlow()

    fun loadApiUsers() {
        viewModelScope.launch {
            _isLoadingApi.value = true
            _apiError.value = null
            try {
                _apiUsers.value = repository.fetchApiUsers()
            } catch (e: java.net.UnknownHostException) {
                _apiError.value = "Sin conexión a Internet. Verifica tu red y vuelve a intentarlo."
            } catch (e: java.net.SocketTimeoutException) {
                _apiError.value = "Tiempo de espera agotado al conectar con el servidor."
            } catch (e: java.io.IOException) {
                _apiError.value = "Error de red al comunicarse con la API."
            } catch (e: Exception) {
                _apiError.value = e.localizedMessage ?: "Error al cargar usuarios de la API"
            } finally {
                _isLoadingApi.value = false
            }
        }
    }

    fun login(username: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val user = repository.login(username, password)
            if (user != null) {
                onSuccess()
            } else {
                onError("Usuario o contraseña incorrectos")
            }
        }
    }

    fun registerUser(user: UserEntity, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                repository.insertUser(user)
                onSuccess()
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Error al registrar usuario")
            }
        }
    }

    fun updateUser(user: UserEntity, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                repository.updateUser(user)
                onSuccess()
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Error al actualizar usuario")
            }
        }
    }

    fun deleteUser(user: UserEntity, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repository.deleteUser(user)
            onSuccess()
        }
    }

    fun selectUser(user: UserEntity?) {
        _selectedUser.value = user
    }
}

class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}