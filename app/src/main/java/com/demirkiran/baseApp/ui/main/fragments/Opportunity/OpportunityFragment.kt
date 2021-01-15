package com.demirkiran.baseApp.ui.main.fragments.Opportunity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.demirkiran.baseApp.R
import com.demirkiran.baseApp.ui.Adapters.MyPictureRecyclerViewAdapter
import com.demirkiran.baseApp.ui.DialogFragments.DialogPictureFragment
import com.demirkiran.baseApp.ui.main.MainActivity
import com.demirkiran.baseApp.ui.main.MainViewModel
import com.demirkiran.baseApp.util.MainHelper
import com.demirkiran.baseApp.util.Resource

class OpportunityFragment : Fragment(R.layout.fragment_opportunity) {

    private lateinit var noDataLayout: LinearLayout
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var viewModel: MainViewModel
    private lateinit var usersAdapter: MyPictureRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_opportunity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        noDataLayout = view.findViewById(R.id.fr_item_no_data_layout)
        userRecyclerView = view.findViewById(R.id.fr_item_recycler_view)
        swipeRefreshLayout = view.findViewById(R.id.fr_item_swipe_refresh)

        setupSwipeRefresh()
        setupRecyclerView()


        usersAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("userId", it.id)
            }
            DialogPictureFragment.newInstance(it).show(childFragmentManager)

        }

        viewModel.allOpportunity.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    (requireActivity() as MainActivity).hideLoading()
                    response.data?.let { Response ->
                        if (Response.photos.isNullOrEmpty()) {
                            showNoDataLayout()
                        } else {
                            hideNoDataLayout()
                            usersAdapter.differ.submitList(Response.photos.toList())
                        }
                    }
                }
                is Resource.Error -> {
                    swipeRefreshLayout.isRefreshing = false
                    (requireActivity() as MainActivity).hideLoading()
                    showNoDataLayout()
                    response.message?.let { message ->
                        MainHelper.showAlertDialog(requireContext(), message)
                    }
                }
                is Resource.Loading -> {
                    if (!swipeRefreshLayout.isRefreshing)
                        (requireActivity() as MainActivity).showLoading()
                }
            }
            response.data = null
            response.message = null
        })

    }

    private fun showNoDataLayout() {
        noDataLayout.visibility = View.VISIBLE
    }

    private fun hideNoDataLayout() {
        noDataLayout.visibility = View.GONE
    }

    private fun setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.allOpportunityFun()
        }
        swipeRefreshLayout.setColorSchemeColors(
            resources.getColor(android.R.color.holo_blue_bright),
            resources.getColor(android.R.color.holo_green_light),
            resources.getColor(android.R.color.holo_orange_light),
            resources.getColor(android.R.color.holo_red_light)
        )
    }

    private fun setupRecyclerView() {
        usersAdapter = MyPictureRecyclerViewAdapter()
        userRecyclerView.apply {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(activity)
            viewModel.allOpportunityFun()
        }
    }

}