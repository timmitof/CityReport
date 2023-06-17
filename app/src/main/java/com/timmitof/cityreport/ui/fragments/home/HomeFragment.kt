package com.timmitof.cityreport.ui.fragments.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.timmitof.cityreport.R
import com.timmitof.cityreport.core.adapters.ReportAdapter
import com.timmitof.cityreport.core.base.BaseFragment
import com.timmitof.cityreport.models.ReportModel
import com.timmitof.cityreport.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val adapter = ReportAdapter()
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerReports.adapter = adapter
    }

    override fun bindData() {
        adapter.setData(listOf(ReportModel("Timmitof", R.mipmap.ic_launcher, "Description", 100)))
    }
}