package com.example.techfinder.adapters

import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.techfinder.databinding.LojaPreviewBinding
import com.example.techfinder.objects.LojaPreview

class ShopsFeedAdapter(private val onClickListener: OnClickListener,private val loc: Location?) :
    ListAdapter<LojaPreview, ShopsFeedAdapter.FeedItemViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedItemViewHolder {
        return FeedItemViewHolder(LojaPreviewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int) {
        val loja = getItem(position)
        holder.binding.root.setOnClickListener {
            onClickListener.onClick(loja)
        }
        holder.bind(loja,loc)
    }


    companion object DiffCallback : DiffUtil.ItemCallback<LojaPreview>() {
        override fun areItemsTheSame(oldItem: LojaPreview, newItem: LojaPreview): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: LojaPreview, newItem: LojaPreview): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class FeedItemViewHolder(var binding: LojaPreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loja: LojaPreview,loc:Location?) {
            binding.loja = loja
            binding.locationUser =loc
            if (loja.listCategorias.size > 0) {
                binding.scrollableCategorias.visibility = View.VISIBLE
                val adapterCategorias = CategoriasAdapter()
                binding.scrollableCategorias.adapter = adapterCategorias
                adapterCategorias.submitList(loja.listCategorias)
                binding.executePendingBindings()
            }else{
                binding.scrollableCategorias.visibility = View.GONE
            }
        }
    }

    interface OnClickListener {
        fun onClick(post: LojaPreview)
    }

}