package com.example.techfinder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.techfinder.databinding.FragmentComentariosBinding
import com.example.techfinder.databinding.FragmentPerfilBinding
import com.example.techfinder.viewModels.ComentariosViewModel
import com.example.techfinder.viewModels.PerfilViewModel

class ComentariosFragment: Fragment() {
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


        return binding.root
    }

}