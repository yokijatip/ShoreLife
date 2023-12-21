package com.example.capstone.ui.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.capstone.data.response.ItemResponse
import com.example.capstone.databinding.ListItemBinding
import com.example.capstone.ui.detail.DetailActivity

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var listItem: List<ItemResponse> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<ItemResponse>) {
        listItem = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val view = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.bind(holder.itemView.context, listItem[position])
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    inner class ViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: ItemResponse) {

            binding.root.setOnClickListener {

                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("extra_id", item.id)
                context.startActivity(intent)

            }

            binding.apply {
//                TODO Buat foto disini
                Glide.with(itemView)
                    .load(item.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(photo)

                listName.text = item.batikName
            }
        }

    }
}