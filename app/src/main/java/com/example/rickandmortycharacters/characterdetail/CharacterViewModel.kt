package com.example.rickandmortycharacters.characterdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycharacters.home.HomeRepository
import com.example.rickandmortycharacters.model.UserModel
import com.example.rickandmortycharacters.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class CharacterDetailViewModel(private val characterDetailRepository: CharacterDetailRepository) : ViewModel() {
    private val _updateFavoriteCharacterLiveData = MutableLiveData<Resource<Boolean>>()
    val updateFavoriteCharacterLiveData: LiveData<Resource<Boolean>>
        get() = _updateFavoriteCharacterLiveData

    private val _getUserLiveData  = MutableLiveData<Resource<UserModel>>()
    val getUserLiveData: LiveData<Resource<UserModel>>
        get() = _getUserLiveData

    private val _getSingleEpisodeLiveData = MutableLiveData<Resource<ResponseBody>>()
    val getSingleEpisodeLiveData: LiveData<Resource<ResponseBody>>
        get() = _getSingleEpisodeLiveData

    fun updateFavoriteCharacter( userId: Int, favoriteId:Int) {
        _updateFavoriteCharacterLiveData.postValue(Resource.loading(data = null))
        viewModelScope.launch {
            try {
                characterDetailRepository.updateFavoriteCharacter(userId,favoriteId)
                _updateFavoriteCharacterLiveData.postValue(Resource.success(data = true))

            } catch (ex:Exception){
                _updateFavoriteCharacterLiveData.postValue(Resource.error(data= null, message = ex.message.toString() ))
            }
        }
    }

    fun getUserById(userId:Int){
        _getUserLiveData.postValue(Resource.loading(data = null))
        viewModelScope.launch {
            try {
                val user = characterDetailRepository.getUserByUserId(userId)
                _getUserLiveData.postValue(Resource.success(data = user))

            } catch (ex:Exception){
                _getUserLiveData.postValue(Resource.error(data= null, message = ex.message.toString() ))
            }
        }
    }
    fun getSingleEpisode(episodeId: Int) {
        _getSingleEpisodeLiveData.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            _getSingleEpisodeLiveData.postValue(characterDetailRepository.getSingleEpisode(episodeId))
        }
    }
}
