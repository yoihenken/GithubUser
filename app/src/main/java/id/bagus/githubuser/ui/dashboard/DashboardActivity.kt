package id.bagus.githubuser.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.bagus.githubuser.databinding.ActivityDashboardBinding
import id.bagus.githubuser.model.User
import id.bagus.githubuser.ui.detail.DetailActivity
import id.bagus.githubuser.ui.detail.DetailActivity.Companion.EXTRA_DATA

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var adapter : UserAdapter
    private var dataUser = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get intent
        dataUser = intent.getParcelableArrayListExtra<User>(EXTRA_USER) as ArrayList<User>
        binding.apply {
            //set adapter
            adapter = UserAdapter(this@DashboardActivity)
            lvUser.adapter = adapter
            //when item clicked
            lvUser.setOnItemClickListener { _, _, i, _ ->
                startActivity(Intent(this@DashboardActivity, DetailActivity::class.java).apply {
                    putExtra(EXTRA_DATA, dataUser[i])
                })
            }
        }
        adapter.userData = dataUser //send data to adapter
    }
    companion object{
        var EXTRA_USER = "extra_user"
    }
}