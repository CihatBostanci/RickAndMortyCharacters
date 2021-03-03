package com.example.rickandmortycharacters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rickandmortycharacters.R
import com.example.rickandmortycharacters.model.UserModel
import com.example.rickandmortycharacters.manager.SharedPreferencesManager.get
import com.example.rickandmortycharacters.manager.SharedPreferencesManager.set
import com.example.rickandmortycharacters.utils.*


const val BASETAG = "BASEFRATAG"

abstract class BaseFragment: Fragment(), ProgressDisplay, IOnBackPressed {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return TextView(activity).apply {
            setText(R.string.hello_blank_fragment)
        }
    }


    override fun showLoading() {
        if (requireActivity() is ProgressDisplay) {
            (activity as ProgressDisplay?)!!.showLoading()
        }
    }

    override fun hideLoading() {
        if (requireActivity() is ProgressDisplay) {
            (activity as ProgressDisplay?)!!.hideLoading()
        }
    }

    override fun onBackPressed(): Boolean {
        requireActivity().onBackPressed()
        return true
    }


    fun showToast(message:String?){
        (requireActivity() as MainActivity).showToast(message)
    }

    //Account Actions
    fun logOutAction(){

        MainApplication.sharedPreferencesManager[USER_EMAIL] = ""
        MainApplication.sharedPreferencesManager[USER_PASSWORD] = ""
        MainApplication.sharedPreferencesManager[USER_NAME]= ""
        MainApplication.sharedPreferencesManager[USER_ID] = -1
        //MainApplication.sharedPreferencesManager[FAVORITE_CHARACTER_ID] = 0

    }

    fun getUserNameFromSharedPref() = MainApplication.sharedPreferencesManager[USER_NAME, ""]

    fun getFavoriteCharacterIdFromSharedPref() = MainApplication.sharedPreferencesManager[FAVORITE_CHARACTER_ID, 0]

    fun getUserIdFromSharedPref() =  MainApplication.sharedPreferencesManager[USER_ID, -1]


    fun setUserOnSharedPref(user: UserModel) {
        MainApplication.sharedPreferencesManager[USER_EMAIL] = user.userEmail
        MainApplication.sharedPreferencesManager[USER_PASSWORD] = user.userPassword
        MainApplication.sharedPreferencesManager[USER_NAME]= user.userName
        MainApplication.sharedPreferencesManager[USER_ID]= user.userId
        MainApplication.sharedPreferencesManager[FAVORITE_CHARACTER_ID] = user.favoriteCharacter
    }
}