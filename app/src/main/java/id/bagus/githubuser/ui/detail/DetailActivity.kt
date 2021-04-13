package id.bagus.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import coil.transform.CircleCropTransformation
import id.bagus.githubuser.databinding.ActivityDetailBinding
import id.bagus.githubuser.model.User

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Get intent
        user = intent.getParcelableExtra<User>(EXTRA_DATA)!!

        binding.apply {
            appBar.title = user.username //Change appbar title
            appBar.setNavigationOnClickListener { finish() } //When back clicked, finish this Activity
            ivAvatar.load(user.avatar){
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            //Set text
            tvName.text = user.name
            tvCompany.text = user.company
            tvFollowers.text = "Followers\t: ${user.followers}"
            tvFollowing.text = "Following\t: ${user.following}"
            tvLocation.text = user.location
            tvRepository.text = "Repository\t: ${user.repository}"
        }
    }

    companion object{
        val EXTRA_DATA = "extra_user"
    }
}