package common.di

import common.domain.MemoryUserRepo
import common.domain.UserRepo

interface ServiceLocator {
    val userRepo: UserRepo
}

fun provideServiceLocator(
    userRepoFactory: () -> UserRepo = { MemoryUserRepo() }
): ServiceLocator {
    return object : ServiceLocator {
        override val userRepo: UserRepo by lazy { userRepoFactory() }
    }
}
