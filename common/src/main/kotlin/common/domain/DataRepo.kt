package common.domain

interface DataRepo {

    suspend fun getData(): String?

    suspend fun saveData(message: String)
}
