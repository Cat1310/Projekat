package com.example.projekat

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Cart : AppCompatActivity() {

    private lateinit var cart: MutableList<Product>
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cart = intent.getSerializableExtra("cart") as MutableList<Product>

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewCart)
        val textViewTotalPrice: TextView = findViewById(R.id.textViewTotalPrice)
        val textViewDeliveryDays: TextView = findViewById(R.id.textViewDeliveryDays)

        cartAdapter = CartAdapter(cart)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@Cart)
            adapter = cartAdapter
        }

        updateTotalPriceAndDeliveryDays(textViewTotalPrice, textViewDeliveryDays)
    }

    private fun updateTotalPriceAndDeliveryDays(textViewTotalPrice: TextView, textViewDeliveryDays: TextView) {
        var totalPrice = 0.0
        var maxDeliveryDays = 0
        cart.forEach { product ->
            totalPrice += product.price * product.quantity
            val deliveryDays = if (product.countryOfOrigin != "Srbija") {
                maxOf(product.deliveryDaysStandard, product.deliveryDaysExpress) + 1
            } else {
                maxOf(product.deliveryDaysStandard, product.deliveryDaysExpress)
            }
            if (deliveryDays > maxDeliveryDays) {
                maxDeliveryDays = deliveryDays
            }
        }
        textViewTotalPrice.text = "Ukupna Cena: $totalPrice"
        textViewDeliveryDays.text = "Dani isporuke: $maxDeliveryDays"
    }
}
