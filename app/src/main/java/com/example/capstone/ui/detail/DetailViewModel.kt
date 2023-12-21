package com.example.capstone.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.capstone.data.Repository.Repository
import com.example.capstone.data.response.DetailResponse
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository) : ViewModel() {

    private val _itemDetail = MutableLiveData<DetailResponse>()
    val itemDetail: LiveData<DetailResponse> get() = _itemDetail

    fun getDetail(id: Int) {
        viewModelScope.launch {
            try {
                _itemDetail.value = repository.getDetailById(id)
            } catch (e: Exception) {
                throw e
            }
        }
    }



}

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}