package com.example.techfinder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.techfinder.R
import com.example.techfinder.adapters.VoteAdapter
import com.example.techfinder.databinding.FragmentTemporaryVoteTestBinding
import com.example.techfinder.objects.Categoria
import com.example.techfinder.objects.TIPOVOTO
import com.example.techfinder.utils.drawVoteIcons
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

    override fun onClickUpvote(categoria: Categoria,holder: VoteAdapter.FeedItemViewHolder) {
        when(categoria.tipoVoto){
            TIPOVOTO.UPVOTE ->{
                viewModel.alterVote(TIPOVOTO.NOVOTE,categoria,args.idLoja) //TIRAR LIKE
                holder.binding.upvote.setBackgroundResource(R.drawable.ic_upvote_outline)
                viewModel.atualizaCategoria(categoria,TIPOVOTO.NOVOTE)
            }
            TIPOVOTO.NOVOTE ->{
                viewModel.alterVote(TIPOVOTO.UPVOTE,categoria,args.idLoja) //POR LIKE
                holder.binding.upvote.setBackgroundResource(R.drawable.ic_upvote)
                viewModel.atualizaCategoria(categoria,TIPOVOTO.UPVOTE)
            }
            TIPOVOTO.DOWNVOTE ->{
                viewModel.alterVote(TIPOVOTO.UPVOTE,categoria,args.idLoja) //TIRAR DOWNVOTE E POR LIKE
                holder.binding.upvote.setBackgroundResource(R.drawable.ic_upvote)
                holder.binding.downvote.setBackgroundResource(R.drawable.ic_downvote_outline)
                viewModel.atualizaCategoria(categoria,TIPOVOTO.UPVOTE)
            }
        }
    }

    override fun onClickDownvote(categoria: Categoria,holder: VoteAdapter.FeedItemViewHolder) {
        when(categoria.tipoVoto){
            TIPOVOTO.UPVOTE ->{
                viewModel.alterVote(TIPOVOTO.DOWNVOTE,categoria,args.idLoja) //TIRAR LIKE E POR DISLIKE
                holder.binding.downvote.setBackgroundResource(R.drawable.ic_downvote)
                holder.binding.upvote.setBackgroundResource(R.drawable.ic_upvote_outline)
                viewModel.atualizaCategoria(categoria,TIPOVOTO.DOWNVOTE)
                holder.binding.upvote
            }
            TIPOVOTO.NOVOTE ->{
                viewModel.alterVote(TIPOVOTO.DOWNVOTE,categoria,args.idLoja) //POR DOWNVOTE
                holder.binding.downvote.setBackgroundResource(R.drawable.ic_downvote)
                viewModel.atualizaCategoria(categoria,TIPOVOTO.DOWNVOTE)
            }
            TIPOVOTO.DOWNVOTE ->{
                viewModel.alterVote(TIPOVOTO.NOVOTE,categoria,args.idLoja) //TIRAR DOWNVOTE
                holder.binding.downvote.setBackgroundResource(R.drawable.ic_downvote_outline)
                viewModel.atualizaCategoria(categoria,TIPOVOTO.NOVOTE)
            }
        }
    }


}