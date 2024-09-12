package common.di

import common.domain.MemoryDataRepo
import common.domain.DataRepo

interface ServiceLocator {
    val userRepo: DataRepo
}

fun provideServiceLocator(
    userRepoFactory: () -> DataRepo = { MemoryDataRepo() }
): ServiceLocator {
    return object : ServiceLocator {
        override val userRepo: DataRepo by lazy { userRepoFactory() }
    }
}
