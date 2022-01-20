package com.example.techfinder.fragments

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.techfinder.viewModels.LojaInfoViewModel

class LojaInfoFragment : Fragment() {

    private val viewModel : LojaInfoViewModel by lazy {
        ViewModelProvider(this).get(LojaInfoViewModel::class.java)
    }
}