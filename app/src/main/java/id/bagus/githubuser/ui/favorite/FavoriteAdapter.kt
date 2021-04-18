package id.bagus.githubuser.ui.favorite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import id.bagus.githubuser.R
import id.bagus.githubuser.databinding.ItemListBinding
import id.bagus.githubuser.model.FavoriteDataSave
import id.bagus.githubuser.ui.detail.DetailActivity

class FavoriteAdapter(
    private val listData : List<FavoriteDataSave>,
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
            itemContain.setOnClickListener {
                context.startActivity(Intent(context, DetailActivity::class.java)
                    .apply { putExtra(DetailActivity.EXTRA_USER, fav.login) }
                )
            }
        }
    }

    override fun getItemCount(): Int = listData.size

}