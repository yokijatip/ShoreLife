package com.example.capstone.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.capstone.data.Repository.Repository
import com.example.capstone.data.response.ItemResponse
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _itemsLiveData = MutableLiveData<List<ItemResponse>>()

    val itemsLiveData: LiveData<List<ItemResponse>> get() = _itemsLiveData

    private val _error = MutableLiveData<String>()

    fun getAllItem() {
        viewModelScope.launch {
            try {
                val response = repository.getAllItem()
                if (response.isSuccessful) {
                    _itemsLiveData.value = response.body()
                } else {
                    _error.value = "Error : ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Exception: ${e.message}"
            }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}