package id.bagus.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import coil.load
import coil.transform.CircleCropTransformation
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.createSkeleton
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import id.bagus.githubuser.R
import id.bagus.githubuser.databinding.ActivityDetailBinding
import id.bagus.githubuser.model.FavoriteDataSave
import id.bagus.githubuser.model.UserResponse
import id.bagus.githubuser.ui.detail.tab.DetailUserFollows
import id.bagus.githubuser.ui.favorite.FavoriteViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var username : String
    private lateinit var skeleton: Skeleton
    private lateinit var skeletonVP: Skeleton
    private val model : DetailViewModel by viewModels()
    private val modelFav : FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSkeletonLayout() //Skeleton for indicator loading

        //Get intent
        username = intent.getStringExtra(EXTRA_USER).toString()
        //Start skeleton
        skeleton.showSkeleton()
        //get All Favorite
        modelFav.getSavedData()
        //Data Detail
        model.apply {
            DetailUserFollows.username = username //send username to Tab Follows
            getDetailUser(username)
            user.observe(this@DetailActivity,{
                fillLayout(it)
                // fill followers and following
                setFollows(it.followers, it.following)
            })
        }
    }

    private fun initSkeletonLayout(){
        skeletonVP = binding.vpFollows.createSkeleton()
        skeletonVP.apply {
            showShimmer = true
            maskCornerRadius = 60f
            shimmerColor = ContextCompat.getColor(this@DetailActivity, R.color.grey_light)
        }
        skeleton = binding.crContainer.createSkeleton()
        skeleton.apply {
            showShimmer = true
            maskCornerRadius = 60f
            shimmerColor = ContextCompat.getColor(this@DetailActivity, R.color.grey_light)
        }
    }

    private fun fillLayout(user : UserResponse){
        binding.apply {
            appBar.title = username //Change appbar title
            appBar.setNavigationOnClickListener { finish() } //When back clicked, finish this Activity

            ivAvatar.load(user.avatarUrl){
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            //Set text
            if(user.name.isNullOrEmpty()) tvName.visibility = View.GONE
            else tvName.text = user.name

            if (user.company.isNullOrEmpty()){
                tvCompany.visibility = View.GONE
                icCompany.visibility = View.GONE
            } else tvCompany.text = user.company

            if (user.location.isNullOrEmpty()){
                tvLocation.visibility = View.GONE
                icLocation.visibility = View.GONE
            } else tvLocation.text = user.location

            tvRepository.text = "${getString(R.string.repository)} ${user.publicRepos}"
            skeleton.showOriginal()

            btnFavorite.apply {
                var currentFav : FavoriteDataSave? = null
                modelFav.saved?.observe(this@DetailActivity, {
                    currentFav = it?.find {find -> find.login == username }

                    if (currentFav != null)btnFavorite.load(R.drawable.ic_baseline_favorite_24)
                    else btnFavorite.load(R.drawable.ic_baseline_favorite_border_24)
                })

                setOnClickListener {
                    if(currentFav == null){
                        modelFav.saveDataLocal(
                            FavoriteDataSave(
                                login = username,
                                avatarUrl = user.avatarUrl
                            ))

                        Toast.makeText(this@DetailActivity, "$username ${getString(R.string.saved)}", Toast.LENGTH_SHORT)
                            .show()
                    }else if(currentFav != null){
                        currentFav?.let { curFav ->  modelFav.removeDataLocal(curFav)}

                        Toast.makeText(this@DetailActivity, "$username ${getString(R.string.unsaved)}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun setFollows(followers : Int? = 0, following : Int? = 0 ){
        val adapterFollows = FragmentPagerItemAdapter(
            supportFragmentManager, FragmentPagerItems.with(this)
                .add("${getString(R.string.followers)} ($followers)", DetailUserFollows::class.java)
                .add("${getString(R.string.following)} ($following)", DetailUserFollows::class.java)
                .create()
        )
        skeletonVP.showSkeleton()
        binding.apply {
            vpFollows.adapter = adapterFollows
            vpFollows.setCurrentItem(0, true)
            tabFollows.setViewPager(vpFollows)
        }

        Handler(mainLooper).postDelayed({
            skeletonVP.showOriginal()
        }, 1000)
    }

    companion object{
        val EXTRA_USER = "extra_user"
    }
}