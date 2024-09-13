package common.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import common.di.ServiceLocator
import common.domain.DataRepo
import common.domain.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HelloScreenViewModel(
    private val dataRepo: DataRepo
) : ViewModel() {

    private val _generatedData = MutableStateFlow<DataState>(DataState.EMPTY)
    val generatedData: StateFlow<DataState> = _generatedData
    private val _readDataState = MutableStateFlow<DataState>(DataState.EMPTY)
    val readDataState: StateFlow<DataState> = _readDataState

    fun reReadData() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _readDataState.value = DataState.LOADING
                val data = dataRepo.getData()
                if (data != null) {
                    DataState.Data(data)
                } else {
                    DataState.EMPTY
                }.let { _readDataState.value = it }
            }
        }
    }

    fun generateAndSave(length: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _generatedData.value = DataState.LOADING
                val data = generateRandomAlphaNumericString(length)
                dataRepo.saveData(data)
                _generatedData.value = DataState.Data(data)
            }
        }
    }

    fun removeData() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _readDataState.value = DataState.LOADING
                _generatedData.value = DataState.LOADING
                dataRepo.removeData()
                _generatedData.value = DataState.EMPTY
                val data = dataRepo.getData()
                if (data != null) {
                    DataState.Data(data)
                } else {
                    DataState.EMPTY
                }.let { _readDataState.value = it }
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
