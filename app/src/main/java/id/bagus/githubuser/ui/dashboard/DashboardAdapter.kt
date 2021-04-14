package id.bagus.githubuser.ui.dashboard

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import id.bagus.githubuser.R
import id.bagus.githubuser.databinding.ItemListBinding
import id.bagus.githubuser.model.UserResponse
import id.bagus.githubuser.ui.detail.DetailActivity

class DashboardAdapter (
    private val listData: List<UserResponse>,
    private val context: Context

) :RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>(){

    private lateinit var binding : ItemListBinding

    class DashboardViewHolder (view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent,false)
        binding = ItemListBinding.bind(view)
        Log.d("Adapter","listData: $listData")
        return DashboardViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val user = listData[position]
        binding.apply {
            ivProfile.load(user.avatarUrl){
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            tvUsername.text = user.login
            itemContain.setOnClickListener {
                context.startActivity(Intent(context, DetailActivity::class.java).apply { putExtra(DetailActivity.EXTRA_USER, user.login)})
            }
        }
    }

    override fun getItemCount(): Int = listData.size


}