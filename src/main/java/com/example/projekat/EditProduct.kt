package com.example.projekat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditProduct : AppCompatActivity() {

    private lateinit var product: Product
    private lateinit var editTextName: EditText
    private lateinit var editTextPrice: EditText
    private lateinit var editTextQuantity: EditText
    private lateinit var editTextExpress: EditText
    private lateinit var editTextStandard: EditText
    private lateinit var editTextCountry: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_product)

        product = intent.getSerializableExtra("product") as Product

        editTextName = findViewById(R.id.editTextName)
        editTextPrice = findViewById(R.id.editTextPrice)
        editTextQuantity = findViewById(R.id.editTextQuantity)
        editTextExpress = findViewById(R.id.editTextExpress)
        editTextStandard = findViewById(R.id.editTextStandard)
        editTextCountry = findViewById(R.id.editTextCountry)

        editTextName.setText(product.name)
        editTextPrice.setText(product.price.toString())
        editTextQuantity.setText(product.quantity.toString())
        editTextExpress.setText(product.deliveryDaysExpress.toString())
        editTextStandard.setText(product.deliveryDaysStandard.toString())
        editTextCountry.setText(product.countryOfOrigin)

        val buttonSave: Button = findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener {
            // Update the product with new values
            product.name = editTextName.text.toString()
            product.price = editTextPrice.text.toString().toDouble()
            product.quantity = editTextQuantity.text.toString().toInt()
            product.deliveryDaysExpress = editTextExpress.text.toString().toInt()
            product.deliveryDaysStandard = editTextStandard.text.toString().toInt()
            product.countryOfOrigin = editTextCountry.text.toString()

            // Pass the updated product back to ProductDetail
            val resultIntent = Intent().apply {
                putExtra("product", product)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
