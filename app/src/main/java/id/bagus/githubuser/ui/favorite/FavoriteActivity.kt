package id.bagus.githubuser.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import id.bagus.githubuser.R
import id.bagus.githubuser.databinding.ActivityFavoriteBinding
import id.bagus.githubuser.model.FavoriteDataSave

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var skeleton : Skeleton
    private val model : FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBar)
        binding.appBar.setNavigationOnClickListener { finish() } //When back clicked, finish this Activity
        initSkeletonLayout()

        model.apply {
            //Get all data
            getSavedData()
            saved?.observe(this@FavoriteActivity, {
                binding.noData.apply {
                    visibility = if (it != null) View.GONE else View.VISIBLE
                }
                recycleData(it)
            })
        }
    }

    private fun initSkeletonLayout(){
        skeleton = binding.rvFav.applySkeleton(R.layout.item_list, 10)
        skeleton.apply {
            showShimmer = false
            maskCornerRadius = 60f
        }
    }

    private fun recycleData(data: List<FavoriteDataSave>){
        binding.rvFav.apply{
            skeleton.showOriginal()
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = FavoriteAdapter(data, this@FavoriteActivity)
        }
    }
}