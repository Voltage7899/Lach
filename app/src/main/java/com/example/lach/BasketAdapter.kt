package com.example.lach

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lach.databinding.ElementBasketBinding
import com.squareup.picasso.Picasso

class BasketAdapter : RecyclerView.Adapter<BasketAdapter.ViewHolder>() {

    private var ListInAdapter = ArrayList<ShopModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_basket, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BasketAdapter.ViewHolder, position: Int) {
        holder.bind(ListInAdapter[position])
    }

    override fun getItemCount(): Int {
        return ListInAdapter.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ElementBasketBinding.bind(itemView)
        fun bind(ticket: ShopModel) {
            binding.ticketName.text = ticket.name
            binding.ticketDesc.text = ticket.description

            binding.ticketPrice.text = ticket.price
            Picasso.get().load(ticket.link).fit().into(binding.ticketImage)



        }
    }

    fun loadListToAdapter(productList: ArrayList<ShopModel>) {
        ListInAdapter = productList
        notifyDataSetChanged()
    }



    fun deleteItem(i: Int): String? {
        var id = ListInAdapter.get(i).name

        ListInAdapter.removeAt(i)

        notifyDataSetChanged()

        return id
    }
}