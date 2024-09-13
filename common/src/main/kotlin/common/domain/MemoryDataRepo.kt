package common.domain

class MemoryDataRepo : DataRepo {

    private var data: String? = null

    override suspend fun getData(): String? = data

    override suspend fun saveData(message: String) {
        data = message
    }

    override suspend fun removeData() {
        data = null
    }
}
