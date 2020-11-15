package com.dicoding.ohmymovies.util

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

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