package id.bagus.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import coil.load
import coil.transform.CircleCropTransformation
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.faltenreich.skeletonlayout.createSkeleton
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import id.bagus.githubuser.R
import id.bagus.githubuser.databinding.ActivityDetailBinding
import id.bagus.githubuser.model.UserResponse
import id.bagus.githubuser.ui.detail.tab.DetailUserFollows

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var username : String
    private lateinit var skeleton: Skeleton
    private lateinit var skeletonVP: Skeleton
    private val model : DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSkeletonLayout() //Skeleton for indicator loading

        //Get intent
        username = intent.getStringExtra(EXTRA_USER).toString()
        //Start skeleton
        skeleton.showSkeleton()
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