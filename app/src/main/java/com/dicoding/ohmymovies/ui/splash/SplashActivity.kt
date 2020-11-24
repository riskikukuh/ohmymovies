package com.dicoding.ohmymovies.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.ui.home.HomeActivity
import com.dicoding.ohmymovies.util.EspressoIdlingResource

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupComponent()
    }

    private fun setupComponent() {
        EspressoIdlingResource.increment()
        object : CountDownTimer(1200, 1200) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                openHome()
                EspressoIdlingResource.decrement()
            }

        }.start()
    }

    private fun openHome(){
        val intent = Intent(this@SplashActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}