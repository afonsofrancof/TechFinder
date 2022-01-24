package com.example.techfinder.adapters

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.techfinder.databinding.ComentarioItemBinding
import com.example.techfinder.objects.Comentario


class ComentariosAdapter :
    ListAdapter<Comentario, ComentariosAdapter.FeedItemViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FeedItemViewHolder {
        return FeedItemViewHolder(ComentarioItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int) {
        val comentario = getItem(position)

        holder.bind(comentario)
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Comentario>() {
        override fun areItemsTheSame(oldItem: Comentario, newItem: Comentario): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Comentario, newItem: Comentario): Boolean {
            return (oldItem.idLoja == newItem.idLoja && oldItem.username == newItem.username)
        }

    }

    class FeedItemViewHolder(var binding: ComentarioItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comentario: Comentario) {
            binding.comentario = comentario
            binding.headerComentario.visibility = GONE
            binding.executePendingBindings()
        }
    }

}