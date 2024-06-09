package com.example.projekat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddProduct : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextPrice: EditText
    private lateinit var editTextQuantity: EditText
    private lateinit var editTextExpress: EditText
    private lateinit var editTextStandard: EditText
    private lateinit var editTextCountry: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        editTextName = findViewById(R.id.editTextName)
        editTextPrice = findViewById(R.id.editTextPrice)
        editTextQuantity = findViewById(R.id.editTextQuantity)
        editTextExpress = findViewById(R.id.editTextExpress)
        editTextStandard = findViewById(R.id.editTextStandard)
        editTextCountry = findViewById(R.id.editTextCountry)

        val buttonAdd: Button = findViewById(R.id.buttonAdd)
        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString()
            val price = editTextPrice.text.toString().toDouble()
            val quantity = editTextQuantity.text.toString().toInt()
            val expressDays = editTextExpress.text.toString().toInt()
            val standardDays = editTextStandard.text.toString().toInt()
            val country = editTextCountry.text.toString()

            val newProduct = Product(
                id = (System.currentTimeMillis() / 1000).toInt(), // Jedinstveni ID
                name = name,
                price = price,
                quantity = quantity,
                deliveryDaysExpress = expressDays,
                deliveryDaysStandard = standardDays,
                countryOfOrigin = country
            )

            val resultIntent = Intent().apply {
                putExtra("product", newProduct)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
