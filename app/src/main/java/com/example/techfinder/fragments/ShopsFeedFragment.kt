package com.example.techfinder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.techfinder.R
import com.example.techfinder.activities.MainActivity
import com.example.techfinder.adapters.ShopsFeedAdapter
import com.example.techfinder.databinding.FragmentShopsFeedBinding
import com.example.techfinder.objects.LojaPreview
import com.example.techfinder.viewModels.ShopsFeedViewModel


class ShopsFeedFragment : Fragment(), ShopsFeedAdapter.OnClickListener {

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
        val adapter = ShopsFeedAdapter(this)
        binding.feed.adapter = adapter
        viewModel.getLojasPreview()
        viewModel.lojaLista.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        return binding.root
    }

    override fun onClick(post: LojaPreview) {
        (activity as MainActivity).findNavController(R.id.host_fragment)
            .navigate(R.id.lojaInfoFragment)
    }


}


