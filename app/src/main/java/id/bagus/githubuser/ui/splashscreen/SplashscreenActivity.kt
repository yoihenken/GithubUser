package id.bagus.githubuser.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import id.bagus.githubuser.R
import id.bagus.githubuser.databinding.ActivitySplashscreenBinding
import id.bagus.githubuser.model.User
import id.bagus.githubuser.ui.dashboard.DashboardActivity
import id.bagus.githubuser.ui.dashboard.DashboardActivity.Companion.EXTRA_USER

class SplashscreenActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get data from string
        val username = resources.getStringArray(R.array.username)
        val name = resources.getStringArray(R.array.name)
        val location = resources.getStringArray(R.array.location)
        val repository = resources.getStringArray(R.array.repository)
        val company = resources.getStringArray(R.array.company)
        val followers = resources.getStringArray(R.array.followers)
        val following = resources.getStringArray(R.array.following)
        val avatar = resources.getStringArray(R.array.avatar)

        //create get object
        val dataUser = arrayListOf<User>()
        for (i in username.indices){
            dataUser.add(
                User(
                    username[i], name[i], location[i], repository[i], company[i], followers[i], following[i], avatar[i]
                )
            )
        }

        Handler(mainLooper).postDelayed({
            finish() //end activity
            startActivity(Intent(this, DashboardActivity::class.java).apply {
                putExtra(EXTRA_USER, dataUser)
            }) //move intent
        }, 3000)

    }
}