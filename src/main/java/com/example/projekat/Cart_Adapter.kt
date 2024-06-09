package com.example.projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(private val cart: List<Product>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewCartName)
        val textViewQuantity: TextView = itemView.findViewById(R.id.textViewCartQuantity)
        val textViewTotalPrice: TextView = itemView.findViewById(R.id.textViewCartTotalPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_cart_product, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cart[position]
        holder.textViewName.text = "Naziv: ${product.name}"
        holder.textViewQuantity.text = "Količina: ${product.quantity}"
        holder.textViewTotalPrice.text = "Ukupna Cena: ${product.price * product.quantity}"
    }

    override fun getItemCount() = cart.size
}
