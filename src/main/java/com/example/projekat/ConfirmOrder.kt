package com.example.projekat

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmOrder : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Pretpostavljamo da smo prosledili podatke o kupcu putem Intent-a
        val name = intent.getStringExtra("name")
        val surname = intent.getStringExtra("surname")
        val email = intent.getStringExtra("email")
        val street = intent.getStringExtra("street")
        val country = intent.getStringExtra("country")

        val cartItems = intent.getSerializableExtra("cartItems") as? List<Product> ?: emptyList()

        val mainLayout = LinearLayout(this).apply {
            id = View.generateViewId()
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16))
        }

        // Prikaz podataka o kupcu
        val customerInfoLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8))
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        val textViewName = TextView(this).apply {
            text = "Ime: $name"
            textSize = 16f
        }

        val textViewSurname = TextView(this).apply {
            text = "Prezime: $surname"
            textSize = 16f
        }

        val textViewEmail = TextView(this).apply {
            text = "Email: $email"
            textSize = 16f
        }

        val textViewStreet = TextView(this).apply {
            text = "Ulica i broj: $street"
            textSize = 16f
        }

        val textViewCountry = TextView(this).apply {
            text = "Država: $country"
            textSize = 16f
        }

        customerInfoLayout.addView(textViewName)
        customerInfoLayout.addView(textViewSurname)
        customerInfoLayout.addView(textViewEmail)
        customerInfoLayout.addView(textViewStreet)
        customerInfoLayout.addView(textViewCountry)

        mainLayout.addView(customerInfoLayout)

        // Prikaz proizvoda i ukupne cene
        var totalPrice = 0.0
        cartItems.forEach { product ->
            totalPrice += product.price

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

        val textViewTotalPrice = TextView(this).apply {
            text = "Ukupna cena: $totalPrice"
            textSize = 18f
        }

        val buttonFinalize = Button(this).apply {
            text = "Finalizuj porudžbinu"
            setOnClickListener {
                // Ovdje biste obradili finalizaciju porudžbine
                // Možete dodati logiku za čuvanje porudžbine u bazi podataka ili slanje potvrde korisniku
            }
        }

        mainLayout.addView(textViewTotalPrice)
        mainLayout.addView(buttonFinalize)

        setContentView(mainLayout)
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}
