package common.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MemoryUserRepo : UserRepo {

    private val stateFlow = MutableStateFlow(User.EMPTY)

    override fun getUser(): Flow<User> = stateFlow

    override suspend fun saveUser(user: User) {
        stateFlow.value = user
    }
}
