package com.example.techfinder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.techfinder.databinding.VotoItemBinding
import com.example.techfinder.objects.Categoria

class VoteAdapter(private val onClickListener: OnClickListener):
    ListAdapter<Categoria, VoteAdapter.FeedItemViewHolder>(DiffCallback) {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): FeedItemViewHolder {
            return FeedItemViewHolder(VotoItemBinding.inflate(LayoutInflater.from(parent.context)))
        }

        override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int) {
            val categoria = getItem(position)
            holder.binding.upvote.setOnClickListener {
                onClickListener.onClickUpvote(categoria)
            }
            holder.binding.downvote.setOnClickListener {
                onClickListener.onClickDownvote(categoria)
            }
            holder.bind(categoria)
        }


        companion object DiffCallback : DiffUtil.ItemCallback<Categoria>() {
            override fun areItemsTheSame(oldItem: Categoria, newItem: Categoria): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Categoria, newItem: Categoria): Boolean {
                return oldItem.nomeCategoria == newItem.nomeCategoria
            }

        }

        class FeedItemViewHolder(var binding: VotoItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(categoria: Categoria) {
                binding.categoria = categoria
                binding.executePendingBindings()
            }
        }

    interface OnClickListener{
        fun onClickUpvote(categoria: Categoria)
        fun onClickDownvote(categoria: Categoria)
    }

    }