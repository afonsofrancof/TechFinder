package com.example.techfinder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.techfinder.NavGraphDirections
import com.example.techfinder.R
import com.example.techfinder.activities.LoginActivity
import com.example.techfinder.activities.MainActivity
import com.example.techfinder.databinding.FragmentLoginBinding
import com.example.techfinder.viewModels.LoginViewModel

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding

    private val viewModel : LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)

        binding.loginButton.setOnClickListener {
            binding.usernameInput.text?.let {
                binding.passwordInput.text?.let { password ->
                    if (it.length < 30) {
                        if (password.length < 25) {
                            if (!it.isBlank()) {
                                if (!password.isBlank()) {
                                    viewModel.autenticaUser(it.toString(),password.toString(),(activity as LoginActivity))
                                }else{
                                    Toast.makeText(
                                        (activity as LoginActivity),
                                        "Please insert your password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }else{
                                Toast.makeText(
                                    (activity as LoginActivity),
                                    "Please insert your username",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }else{
                            Toast.makeText(
                                (activity as LoginActivity),
                                "Password exceeds the 25 character limit",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }else{
                        Toast.makeText(
                            (activity as LoginActivity),
                            "Username exceeds the 30 character limit",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } ?: run {
                    Toast.makeText(
                        (activity as LoginActivity),
                        "Please insert your password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } ?: run {
                Toast.makeText(
                    (activity as LoginActivity),
                    "Please insert your username",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.registerButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            (activity as LoginActivity).findNavController(R.id.fragment_container_login)
                .navigate(action)
        }

        return binding.root
    }
}