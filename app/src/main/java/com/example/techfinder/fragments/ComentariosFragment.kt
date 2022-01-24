package com.example.techfinder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.techfinder.adapters.ComentariosAdapter
import com.example.techfinder.adapters.ShopsFeedAdapter
import com.example.techfinder.databinding.FragmentComentariosBinding
import com.example.techfinder.databinding.FragmentPerfilBinding
import com.example.techfinder.databinding.FragmentShopsFeedBinding
import com.example.techfinder.viewModels.ComentariosViewModel
import com.example.techfinder.viewModels.PerfilViewModel

class ComentariosFragment: Fragment(){
    lateinit var binding: FragmentComentariosBinding

    private val viewModel: ComentariosViewModel by lazy {
        ViewModelProvider(this).get(ComentariosViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentComentariosBinding.inflate(inflater)
        val adapter = ComentariosAdapter()
        binding.feed.adapter = adapter
        viewModel.comentsLista.observe(viewLifecycleOwner, {
                adapter.submitList(it)
        })
        viewModel.comentsLista.value = null;
        viewModel.getComentarios()

        return binding.root
    }

}