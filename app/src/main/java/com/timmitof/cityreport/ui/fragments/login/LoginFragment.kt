package com.timmitof.cityreport.ui.fragments.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.timmitof.cityreport.core.base.BaseFragment
import com.timmitof.cityreport.databinding.FragmentLoginBinding
import com.timmitof.cityreport.ui.activities.HomeActivity

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            val intent = Intent(requireActivity(), HomeActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}