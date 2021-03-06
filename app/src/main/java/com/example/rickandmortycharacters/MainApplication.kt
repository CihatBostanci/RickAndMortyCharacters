package com.example.rickandmortycharacters

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import com.example.rickandmortycharacters.di.databaseModule
import com.example.rickandmortycharacters.di.networkModule
import com.example.rickandmortycharacters.di.repositoryModule
import com.example.rickandmortycharacters.di.viewModelModule
import com.example.rickandmortycharacters.manager.SharedPreferencesManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


const val MAINAPPLICATIONTAG = "MAINAPPTAG"

class MainApplication : Application() {

    companion object {

        lateinit var sharedPreferencesManager: SharedPreferences

        private var mCurrentActivity: Activity? = null

        fun getApplication(): Companion {
            return this
        }

        fun setCurrentActivity(mCurrentActivity: Activity) {
            this.mCurrentActivity = mCurrentActivity
        }

        fun getCurrentActivity() = this.mCurrentActivity

    }

    override fun onCreate() {
        sharedPreferencesManager = SharedPreferencesManager.defaultPrefs(applicationContext)
        super.onCreate()
        startKoin {
            Log.i(MAINAPPLICATIONTAG, "Koin")
            androidContext(this@MainApplication)
            modules(databaseModule, networkModule, repositoryModule, viewModelModule)
        }
    }

}