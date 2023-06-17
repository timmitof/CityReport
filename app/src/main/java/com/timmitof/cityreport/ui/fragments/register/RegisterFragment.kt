package com.timmitof.cityreport.ui.fragments.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.timmitof.cityreport.core.base.BaseFragment
import com.timmitof.cityreport.core.utils.Status
import com.timmitof.cityreport.databinding.FragmentRegisterBinding
import com.timmitof.cityreport.ui.activities.HomeActivity
import com.timmitof.cityreport.ui.fragments.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            register()
        }
    }

    private fun register() {
        if (binding.loginInput.text.isNullOrBlank() || binding.passwordInput.text.isNullOrBlank()) {
            Toast.makeText(requireContext(), "Заполните поля!", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.login(binding.loginInput.text.toString(), binding.passwordInput.text.toString())
            viewModel.reportRegister.observe(viewLifecycleOwner) {
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
        }
    }
}