package com.example.projekat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Cart : AppCompatActivity() {

    private val cartItems = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val name = intent.getStringExtra("name")
        val surname = intent.getStringExtra("surname")
        val email = intent.getStringExtra("email")
        val street = intent.getStringExtra("street")
        val country = intent.getStringExtra("country")

        cartItems.add(intent.getSerializableExtra("product") as Product)

        val mainLayout = LinearLayout(this).apply {
            id = View.generateViewId()
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16))
        }

        cartItems.forEach { product ->
            val productLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8))
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }

            val textViewName = TextView(this).apply {
                text = "Naziv: ${product.name}"
                textSize = 16f
            }

            val textViewPrice = TextView(this).apply {
                text = "Cena: ${product.price}"
                textSize = 16f
            }

            productLayout.addView(textViewName)
            productLayout.addView(textViewPrice)
            mainLayout.addView(productLayout)
        }

        val buttonConfirm = Button(this).apply {
            text = "Potvrdi porudžbinu"
            setOnClickListener {
                val intent = Intent(this@Cart, ConfirmOrder::class.java).apply {
                    putExtra("name", name)
                    putExtra("surname", surname)
                    putExtra("email", email)
                    putExtra("street", street)
                    putExtra("country", country)
                    putExtra("cartItems", ArrayList(cartItems))
                }
                startActivity(intent)
            }
        }

        mainLayout.addView(buttonConfirm)
        setContentView(mainLayout)
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}
