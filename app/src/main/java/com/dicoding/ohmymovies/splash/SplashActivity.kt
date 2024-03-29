package com.dicoding.ohmymovies.splash

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupComponent()
    }

    private fun setupComponent() {
        object : CountDownTimer(1200, 1200) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                openHome()
            }

        }.start()
    }

    private fun openHome(){
        val intent = Intent(this@SplashActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}