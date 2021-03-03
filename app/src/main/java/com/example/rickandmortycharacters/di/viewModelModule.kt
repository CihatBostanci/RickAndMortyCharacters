package com.example.rickandmortycharacters.di

import com.example.rickandmortycharacters.characterdetail.CharacterDetailViewModel
import com.example.rickandmortycharacters.home.HomeViewModel
import com.example.rickandmortycharacters.login.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { LoginViewModel(loginRepository = get()) }
    viewModel { HomeViewModel(homeRepository = get()) }
    viewModel { CharacterDetailViewModel(characterDetailRepository = get()) }

}