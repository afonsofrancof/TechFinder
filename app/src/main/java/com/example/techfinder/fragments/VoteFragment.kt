package com.example.techfinder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.techfinder.adapters.VoteAdapter
import com.example.techfinder.databinding.FragmentTemporaryVoteTestBinding
import com.example.techfinder.objects.Categoria
import com.example.techfinder.objects.TIPOVOTO
import com.example.techfinder.viewModels.VoteViewModel

class VoteFragment : Fragment(),VoteAdapter.OnClickListener {
    lateinit var binding: FragmentTemporaryVoteTestBinding

    private val viewModel: VoteViewModel by lazy {
        ViewModelProvider(this).get(VoteViewModel::class.java)
    }

    val args by navArgs<VoteFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTemporaryVoteTestBinding.inflate(inflater)
        val adapter = VoteAdapter(this)
        binding.feed.adapter =adapter

        viewModel.listaCategorias.observe(viewLifecycleOwner, { it ->
           adapter.submitList(it)
        })

        viewModel.listaCategorias.value = null
        viewModel.getLoja(args.idLoja)
        viewModel.getCategorias()

        return binding.root
    }

    override fun onClickUpvote(categoria: Categoria) {
        when(categoria.tipoVoto){
            TIPOVOTO.UPVOTE ->{
                viewModel.alterVote(TIPOVOTO.NOVOTE,categoria,args.idLoja) //TIRAR LIKE
            }
            TIPOVOTO.NOVOTE ->{
                viewModel.alterVote(TIPOVOTO.UPVOTE,categoria,args.idLoja) //POR LIKE
            }
            TIPOVOTO.DOWNVOTE ->{
                viewModel.alterVote(TIPOVOTO.UPVOTE,categoria,args.idLoja) //TIRAR DOWNVOTE E POR LIKE
            }
        }
    }

    override fun onClickDownvote(categoria: Categoria) {
        when(categoria.tipoVoto){
            TIPOVOTO.UPVOTE ->{
                viewModel.alterVote(TIPOVOTO.DOWNVOTE,categoria,args.idLoja) //TIRAR LIKE E POR DISLIKE
            }
            TIPOVOTO.NOVOTE ->{
                viewModel.alterVote(TIPOVOTO.DOWNVOTE,categoria,args.idLoja) //POR DOWNVOTE
            }
            TIPOVOTO.DOWNVOTE ->{
                viewModel.alterVote(TIPOVOTO.NOVOTE,categoria,args.idLoja) //TIRAR DOWNVOTE
            }
        }
    }


}