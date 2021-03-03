package com.example.rickandmortycharacters

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rickandmortycharacters.R
import com.example.rickandmortycharacters.utils.ProgressDisplay

abstract class BaseActivity : AppCompatActivity(), ProgressDisplay {

    var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onStart() {
        super.onStart()
        MainApplication.setCurrentActivity(this)
    }


    override fun showLoading() {
        progressBar = findViewById(R.id.progressBar)
        progressBar!!.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar = findViewById(R.id.progressBar)
        progressBar!!.visibility = View.INVISIBLE
    }

    fun showToast(message:String?){
        Toast.makeText(this, message ?: "Message is null", Toast.LENGTH_LONG).show()
    }

}