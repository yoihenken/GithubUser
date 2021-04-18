package id.bagus.githubuser.ui.dashboard

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import id.bagus.githubuser.R
import id.bagus.githubuser.databinding.ActivityDashboardBinding
import id.bagus.githubuser.model.UserResponse
import id.bagus.githubuser.ui.favorite.FavoriteActivity
import id.bagus.githubuser.ui.settings.SettingsActivity
import id.bagus.githubuser.utils.Helpers.hideKeyboard

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var skeleton: Skeleton
    private val model : DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBar)
        initSkeletonLayout() //Skeleton for indicator loading
        model.user.observe(this, {
            if(it.isEmpty()){
                skeleton.showOriginal()
                binding.noData.visibility = View.VISIBLE
            }else{
                binding.noData.visibility = View.GONE
            }
            recycleData(it)
        })

    }

    private fun initSkeletonLayout(){
        skeleton = binding.lvUser.applySkeleton(R.layout.item_list, 10)
        skeleton.apply {
            showShimmer = true
            maskCornerRadius = 60f
        }
    }

    private fun recycleData(data: List<UserResponse>){
        binding.lvUser.apply{
            skeleton.showOriginal()
            layoutManager = LinearLayoutManager(this@DashboardActivity)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = DashboardAdapter(data, this@DashboardActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        // Search View
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = resources.getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    skeleton.showSkeleton()
                    model.getSearchUser(query.trim())
                    this@DashboardActivity.hideKeyboard(this@apply) //hide keyboard
                    return true
                }
                override fun onQueryTextChange(newText: String): Boolean {
                    skeleton.showSkeleton()
                    model.getSearchUser(newText)
                    return false
                }
            })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
            }
            R.id.settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }

        return super.onOptionsItemSelected(item)
    }
}