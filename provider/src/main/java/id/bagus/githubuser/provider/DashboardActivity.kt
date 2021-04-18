package id.bagus.githubuser.provider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import id.bagus.githubuser.provider.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDashboardBinding
    private lateinit var skeleton : Skeleton
    private val model : DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSkeletonLayout()
        model.apply {
            instance(this@DashboardActivity)
            getSavedData(this@DashboardActivity)

            saved.observe(this@DashboardActivity, {
                binding.noData.apply { visibility = if (it != null) View.GONE else View.VISIBLE }
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

    private fun recycleData(data: List<FavoriteData>){
        binding.rvFav.apply{
            skeleton.showOriginal()
            layoutManager = LinearLayoutManager(this@DashboardActivity)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = FavoriteAdapter(data, this@DashboardActivity)
        }
    }

}