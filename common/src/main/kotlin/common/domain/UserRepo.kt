package common.domain

import kotlinx.coroutines.flow.Flow

interface UserRepo {

    fun getUser(): Flow<User>

    suspend fun saveUser(user: User)
}
