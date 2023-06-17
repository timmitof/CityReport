package com.timmitof.cityreport.ui.fragments.authorization

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.timmitof.cityreport.R
import com.timmitof.cityreport.core.adapters.ViewPagerAdapter
import com.timmitof.cityreport.core.base.BaseFragment
import com.timmitof.cityreport.databinding.FragmentAuthorizationBinding
import com.timmitof.cityreport.ui.fragments.login.LoginFragment
import com.timmitof.cityreport.ui.fragments.register.RegisterFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationFragment() :
    BaseFragment<FragmentAuthorizationBinding>(FragmentAuthorizationBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapterViewPager = ViewPagerAdapter(this)
        val fragments = listOf<Fragment>(LoginFragment(), RegisterFragment())
        adapterViewPager.setFragments(fragments)
        binding.viewPagerAuth.adapter = adapterViewPager
        binding.tabsAuth.addTab(binding.tabsAuth.newTab().setText(R.string.login))
        binding.tabsAuth.addTab(binding.tabsAuth.newTab().setText(R.string.register))
        binding.tabsAuth.addOnTabSelectedListener(listenerTab)
    }

    val listenerTab = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            binding.viewPagerAuth.currentItem = tab.position
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
        }

    }
}