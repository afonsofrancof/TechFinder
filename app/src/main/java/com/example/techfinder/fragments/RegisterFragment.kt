package com.example.techfinder.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.techfinder.activities.LoginActivity
import com.example.techfinder.activities.MainActivity
import com.example.techfinder.databinding.FragmentRegisterBinding
import com.example.techfinder.viewModels.RegisterViewModel

class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding

    val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)

        binding.registerButton.setOnClickListener {
            val username = binding.usernameInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val email = binding.emailInput.text.toString()
            val morada = binding.moradaInput.text.toString()
            val nome = binding.nomeInput.text.toString()
            if (email.isNullOrBlank() || morada.isNullOrBlank() || nome.isNullOrBlank() || password.isNullOrBlank() || username.isNullOrBlank()) return@setOnClickListener
            if (username.length > 30 || password.length > 25 || nome.length > 25 || email.length > 100 || morada.length > 150) {
                Toast.makeText(
                    (activity as MainActivity),
                    "Um dos campos inseridos ultrapassa o limite de caracters",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else {
                val success = viewModel.register(username, password, nome, morada, email)
                if(success){
                    (activity as LoginActivity).startActivity(Intent(activity, MainActivity::class.java))
                    (activity as LoginActivity).finish()
                }
            }

        }

        return binding.root
    }
}