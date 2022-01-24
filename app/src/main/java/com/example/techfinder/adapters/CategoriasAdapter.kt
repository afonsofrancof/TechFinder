package com.example.techfinder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.techfinder.databinding.CategoriaBinding
import com.example.techfinder.objects.Categoria

class CategoriasAdapter :
    ListAdapter<Categoria, CategoriasAdapter.FeedItemViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedItemViewHolder {
        return FeedItemViewHolder(CategoriaBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int) {
        val categoria = getItem(position)
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

    class FeedItemViewHolder(var binding: CategoriaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(categoria: Categoria) {
            binding.categoria = categoria
            binding.executePendingBindings()
        }
    }

}