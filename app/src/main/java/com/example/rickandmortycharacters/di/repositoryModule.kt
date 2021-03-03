package com.example.rickandmortycharacters.di

import com.example.rickandmortycharacters.api.CharacterService
import com.example.rickandmortycharacters.api.EpisodeService
import com.example.rickandmortycharacters.characterdetail.CharacterDetailRepository
import com.example.rickandmortycharacters.database.UserDAO
import com.example.rickandmortycharacters.home.HomeRepository
import com.example.rickandmortycharacters.login.LoginRepository
import org.koin.dsl.module

val repositoryModule = module {


    fun provideLoginRepository(dao: UserDAO): LoginRepository {
        return LoginRepository(dao)
    }

    fun provideHomeRepository(characterService: CharacterService, userDAO: UserDAO): HomeRepository {
        return HomeRepository(characterService,userDAO)
    }
    fun provideCharacterDetailRepository( userDAO: UserDAO, episodeService: EpisodeService): CharacterDetailRepository {
        return CharacterDetailRepository(userDAO,episodeService)
    }


    single { provideLoginRepository(get()) }
    single { provideHomeRepository(get(), get()) }
    single { provideCharacterDetailRepository(get(), get()) }


}