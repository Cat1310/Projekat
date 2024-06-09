package com.example.projekat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextSurname = findViewById<EditText>(R.id.editTextSurname)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextStreet = findViewById<EditText>(R.id.editTextStreet)
        val editTextCountry = findViewById<EditText>(R.id.editTextCountry)
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            val name = editTextName.text.toString()
            val surname = editTextSurname.text.toString()
            val email = editTextEmail.text.toString()
            val street = editTextStreet.text.toString()
            val country = editTextCountry.text.toString()

            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || street.isEmpty() || country.isEmpty()) {
                Log.e("MainActivity", "Sva polja moraju biti popunjena!")
            } else {
                val intent = Intent(this@MainActivity, ProductList::class.java).apply {
                    putExtra("name", name)
                    putExtra("surname", surname)
                    putExtra("email", email)
                    putExtra("street", street)
                    putExtra("country", country)
                }
                startActivity(intent)
            }
        }
    }
}
