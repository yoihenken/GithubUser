package id.bagus.githubuser.ui.detail.tab

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem
import id.bagus.githubuser.R
import id.bagus.githubuser.databinding.FragmentDetailUserFollowsBinding
import id.bagus.githubuser.model.UserResponse
import id.bagus.githubuser.ui.dashboard.DashboardAdapter
import id.bagus.githubuser.ui.detail.DetailViewModel


class DetailUserFollows : Fragment() {

    private lateinit var binding: FragmentDetailUserFollowsBinding
    private val model : DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailUserFollowsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = FragmentPagerItem.getPosition(arguments)
        showFragments(index)
    }

    private fun showFragments(index: Int) {
        when(index){
            0 -> {
                model.apply {
                    getUserFollower(username)
                    follower.observe(viewLifecycleOwner){
                        recycleData(it)
                    }
                }
            }
            1 -> {
                model.apply {
                    getUserFollowing(username)
                    following.observe(viewLifecycleOwner){
                        recycleData(it)
                    }
                }
            }
        }
    }

    private fun recycleData(data: List<UserResponse>) = with(binding.rvFollows){
        layoutManager = LinearLayoutManager(activity)
        setHasFixedSize(true)
        adapter = DashboardAdapter(data, requireContext())
    }

    companion object {
        var username = "yoihenken"
    }
}