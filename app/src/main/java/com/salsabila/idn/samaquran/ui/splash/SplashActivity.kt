package com.salsabila.idn.samaquran.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.salsabila.idn.samaquran.R
import com.salsabila.idn.samaquran.databinding.ActivitySplashBinding
import com.salsabila.idn.samaquran.ui.onboarding.OnboardingActivity

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()) //buat ngambil waktu
            .postDelayed({
                startActivity(Intent(this,OnboardingActivity::class.java))
                finish() //buat matiin activity
            }, SPLASH_TIME_OUT)
    }
    companion object{
        private const val SPLASH_TIME_OUT = 1500L // i,5 detik
    }
}