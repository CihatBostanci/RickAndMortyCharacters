package com.example.rickandmortycharacters.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycharacters.model.UserModel
import com.example.rickandmortycharacters.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {


    private val _getAllCharactersLiveData = MutableLiveData<Resource<ResponseBody>>()
    val getAllCharactersLiveData: LiveData<Resource<ResponseBody>>
        get() = _getAllCharactersLiveData

    private val _getAllCharactersWithPageLiveData = MutableLiveData<Resource<ResponseBody>>()
    val getAllCharactersWithPageLiveData: LiveData<Resource<ResponseBody>>
        get() = _getAllCharactersWithPageLiveData

    private val _getFilterByNameAndStatusAllCharactersWithPageLiveData =
        MutableLiveData<Resource<ResponseBody>>()
    val getFilterByNameAndStatusAllCharactersWithPageLiveData: LiveData<Resource<ResponseBody>>
        get() = _getFilterByNameAndStatusAllCharactersWithPageLiveData

    private val _updateFavoriteCharacterLiveData = MutableLiveData<Resource<Boolean>>()
    val updateFavoriteCharacterLiveData: LiveData<Resource<Boolean>>
        get() = _updateFavoriteCharacterLiveData

    private val _getUserLiveData  = MutableLiveData<Resource<UserModel>>()
    val getUserLiveData: LiveData<Resource<UserModel>>
        get() = _getUserLiveData

    fun getAllCharacters() {
        _getAllCharactersLiveData.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            _getAllCharactersLiveData.postValue(homeRepository.getAllCharacters())
        }
    }


    fun getAllCharactersWithPage(pageNumber: Int) {
        _getAllCharactersWithPageLiveData.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            _getAllCharactersWithPageLiveData.postValue(homeRepository.getAllCharacters(pageNumber))
        }
    }

    fun filterByNameAndStatus(name: String?, status: String?) {
        _getFilterByNameAndStatusAllCharactersWithPageLiveData.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            _getFilterByNameAndStatusAllCharactersWithPageLiveData.postValue(
                homeRepository.filterAllCharacters(
                    name,
                    status
                )
            )
        }
    }


}