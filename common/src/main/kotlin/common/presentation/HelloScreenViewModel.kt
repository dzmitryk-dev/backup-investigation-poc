package common.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import common.di.ServiceLocator
import common.domain.User
import common.domain.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class HelloScreenViewModel(
    private val userRepo: UserRepo
) : ViewModel() {

    val user: StateFlow<User> = userRepo.getUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = User.EMPTY
        )


    fun saveUser(name: String, surname: String, age: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                userRepo.saveUser(User(name, surname, age.toInt(Random.nextInt(10, 15)), generateRandomAlphaNumericString(10)))
            }
        }
    }

    private fun generateRandomAlphaNumericString(length: Int): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return List(length) { charset.random() }.joinToString("")
    }


    @Suppress("UNCHECKED_CAST")
    companion object {

        fun provideFactoryProvide(serviceLocator: ServiceLocator): () -> ViewModelProvider.Factory {
            return {
                object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return HelloScreenViewModel(serviceLocator.userRepo) as T
                    }
                }
            }
        }
    }

}
