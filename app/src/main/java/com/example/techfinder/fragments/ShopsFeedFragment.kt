package com.example.techfinder.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.techfinder.databinding.FragmentShopsFeedBinding
import com.example.techfinder.viewModels.ShopsFeedViewModel


class ShopsFeedFragment : Fragment() {

    lateinit var binding: FragmentShopsFeedBinding

    private val viewModel: ShopsFeedViewModel by lazy {
        ViewModelProvider(this).get(ShopsFeedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopsFeedBinding.inflate(inflater)


        return binding.root
    }


}


