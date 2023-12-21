package com.example.capstone.ui.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.data.response.ItemResponse
import com.example.capstone.databinding.ListFactBinding
import com.example.capstone.ui.detail.DetailActivity

class FactAdapter : RecyclerView.Adapter<FactAdapter.ViewHolder>() {

    private var listItem: List<ItemResponse> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<ItemResponse>) {
        listItem = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ListFactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, item: ItemResponse) {
            binding.root.setOnClickListener {
//                TODO Ini bisa di ganti jadi setiap item bisa di klik,nah kamu bisa ganti mau di klik di arahin kemana, sementara aku isi pake Log aja aja
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("extra_id", item.id)
                context.startActivity(intent)
            }

            binding.apply {

//                TODO Ini buat foto
//                Glide.with(itemView)
//                    .load(item.photoUrl)
//                    .transition(DrawableTransitionOptions.withCrossFade())
//                    .centerCrop()
//                    .into(imageFact)

                titleFact.text = item.batikName
                descFact.text = item.content
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListFactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(holder.itemView.context, listItem[position])
    }

}