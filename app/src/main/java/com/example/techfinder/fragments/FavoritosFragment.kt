package com.example.techfinder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.techfinder.databinding.FragmentComentariosBinding
import com.example.techfinder.databinding.FragmentFavoritosBinding
import com.example.techfinder.viewModels.ComentariosViewModel
import com.example.techfinder.viewModels.FavoritosViewModel

class FavoritosFragment : Fragment() {
    lateinit var binding: FragmentFavoritosBinding

    private val viewModel: FavoritosViewModel by lazy {
        ViewModelProvider(this).get(FavoritosViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritosBinding.inflate(inflater)


        return binding.root
    }
}