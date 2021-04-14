package id.bagus.githubuser.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import id.bagus.githubuser.databinding.ActivitySplashscreenBinding
import id.bagus.githubuser.ui.dashboard.DashboardActivity

class SplashscreenActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(mainLooper).postDelayed({
            finish() //end activity
            startActivity(Intent(this, DashboardActivity::class.java)) //move intent
        }, 3000)

    }
}