package com.timmitof.cityreport.ui.fragments.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.timmitof.cityreport.core.base.BaseFragment
import com.timmitof.cityreport.core.utils.Status
import com.timmitof.cityreport.databinding.FragmentLoginBinding
import com.timmitof.cityreport.ui.activities.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            login()
        }
    }

    private fun login() {
        if (binding.loginInput.text.isNullOrBlank() || binding.passwordInput.text.isNullOrBlank()) {
            Toast.makeText(requireContext(), "Заполните поля!", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.login(binding.loginInput.text.toString(), binding.passwordInput.text.toString())
        }
    }

    override fun bindData() {
        super.bindData()
        viewModel.reportLogin.observe(viewLifecycleOwner) {
            when(it.status) {
                Status.SUCCESSFUL -> {
                    startActivity(Intent(requireActivity(), HomeActivity::class.java))
                    requireActivity().finish()
                }
                Status.FAILURE -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                }
            }
        }
        viewModel.reportUser.observe(viewLifecycleOwner) {
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
            requireActivity().finish()
        }
    }
}