package com.example.techfinder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.techfinder.databinding.FragmentLojaInfoBinding
import com.example.techfinder.databinding.FragmentShopsFeedBinding
import com.example.techfinder.viewModels.LojaInfoViewModel

class LojaInfoFragment : Fragment() {

    lateinit var binding: FragmentLojaInfoBinding

    private val viewModel : LojaInfoViewModel by lazy {
        ViewModelProvider(this).get(LojaInfoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLojaInfoBinding.inflate(inflater)


        return binding.root
    }
}