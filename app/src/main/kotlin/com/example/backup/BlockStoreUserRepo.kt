package com.example.backup

import common.domain.User
import common.domain.UserRepo
import kotlinx.coroutines.flow.Flow

class BlockStoreUserRepo : UserRepo {

    override fun getUser(): Flow<User> {
        TODO("Not yet implemented")
    }

    override suspend fun saveUser(user: User) {
        TODO("Not yet implemented")
    }
}
