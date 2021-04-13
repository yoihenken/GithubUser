package id.bagus.githubuser.ui.dashboard

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import coil.load
import coil.transform.CircleCropTransformation
import id.bagus.githubuser.R
import id.bagus.githubuser.databinding.ItemListBinding
import id.bagus.githubuser.model.User

class UserAdapter internal constructor(private val context: Context) : BaseAdapter(){
    internal var userData = arrayListOf<User>()

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
        var itemView = view
        if (itemView == null ) itemView = LayoutInflater.from(context).inflate(R.layout.item_list, viewGroup, false)

        val viewHolder = ViewHolder(itemView as View)
        val user = getItem(position) as User
        viewHolder.bind(user)
        return itemView
    }

    override fun getCount(): Int = userData.size

    override fun getItem(p0: Int): Any = userData[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    private inner class ViewHolder internal constructor(view: View){
        val binding = ItemListBinding.bind(view)

        internal fun bind(user: User) {
            binding.apply {
                ivProfile.load(user.avatar){
                    transformations(CircleCropTransformation())
                }
                tvUsername.text = user.name
                tvFollowing.text = "Following\t: " + user.following
                tvFollowers.text = "Followers\t: " + user.followers
            }
        }
    }
}