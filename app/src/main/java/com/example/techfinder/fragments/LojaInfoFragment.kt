package com.example.techfinder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.techfinder.NavGraphArgs
import com.example.techfinder.databinding.FragmentLojaInfoBinding
import com.example.techfinder.databinding.FragmentShopsFeedBinding
import com.example.techfinder.viewModels.LojaInfoViewModel

class LojaInfoFragment : Fragment() {

    lateinit var binding: FragmentLojaInfoBinding

    private val viewModel : LojaInfoViewModel by lazy {
        ViewModelProvider(this).get(LojaInfoViewModel::class.java)
    }

    val args by navArgs<LojaInfoFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLojaInfoBinding.inflate(inflater)

        binding.loja = viewModel.getLoja(args.idLoja)
        binding.abertura.text = args.abertura
        binding.fecho.text = args.fecho

        return binding.root
    }
}