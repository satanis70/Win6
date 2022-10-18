package com.example.win6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ProgressBar

class SplashActivity : AppCompatActivity() {

    var progressMain: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        progressBar.max = 300
        val timer = object : CountDownTimer(3000, 1000) {

            override fun onTick(p0: Long) {
                progressMain += 100
                progressBar.progress = progressMain
            }

            override fun onFinish() {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }

        }
        timer.start()
    }
}