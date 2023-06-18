package com.timmitof.cityreport.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.timmitof.cityreport.core.adapters.ReportAdapter
import com.timmitof.cityreport.core.base.BaseFragment
import com.timmitof.cityreport.core.utils.Status
import com.timmitof.cityreport.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private var adapter: ReportAdapter ?= null
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ReportAdapter {
            viewModel.sendImportance(it)
        }
        binding.recyclerReports.adapter = adapter
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getAllComplaints()
        }
    }

    override fun bindData() {
        viewModel.reportComplaints.observe(viewLifecycleOwner) {
            when(it.status) {
                Status.SUCCESSFUL -> {
                    binding.swipeRefresh.isRefreshing = false
                    it.data?.let { list -> adapter?.setData(list.sortedByDescending { sort -> sort.countLike }) }
                }
                Status.FAILURE -> {
                    binding.swipeRefresh.isRefreshing = false
                }
                Status.LOADING -> {
                    binding.swipeRefresh.isRefreshing = true
                }
            }
        }
        viewModel.reportImportance.observe(viewLifecycleOwner) {
            viewModel.getAllComplaints()
        }
    }
}