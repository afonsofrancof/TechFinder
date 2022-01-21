package com.example.techfinder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.techfinder.activities.MainActivity
import com.example.techfinder.databinding.FragmentPerfilBinding
import com.example.techfinder.databinding.FragmentShopsFeedBinding
import com.example.techfinder.viewModels.PerfilViewModel
import com.example.techfinder.viewModels.ShopsFeedViewModel

class PerfilFragment : Fragment(){

    lateinit var binding: FragmentPerfilBinding

    private val viewModel: PerfilViewModel by lazy {
        ViewModelProvider(this).get(PerfilViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilBinding.inflate(inflater)

        return binding.root
    }

}