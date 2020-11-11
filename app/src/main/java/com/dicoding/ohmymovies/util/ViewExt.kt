package com.dicoding.ohmymovies.util

import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController

fun AppCompatActivity.setupToolbar(v : Toolbar, title : String?, canPop : Boolean = true, pop : () -> Unit = {}){
    apply {
        setTitle(title)
        setSupportActionBar(v)
        if (canPop){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
    }
    v.setNavigationOnClickListener {
        pop.invoke()
    }
}