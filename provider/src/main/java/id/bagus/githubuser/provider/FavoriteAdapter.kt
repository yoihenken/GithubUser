package id.bagus.githubuser.provider

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import id.bagus.githubuser.provider.databinding.ItemListBinding

class FavoriteAdapter(
    private val listData : List<FavoriteData>,
    private val context: Context
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private lateinit var binding : ItemListBinding

    class FavoriteViewHolder (view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent,false)
        binding = ItemListBinding.bind(view)
        return FavoriteViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val fav = listData[position]
        binding.apply {
            ivProfile.load(fav.avatarUrl){
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            tvUsername.text = fav.login
        }
    }

    override fun getItemCount(): Int = listData.size

}