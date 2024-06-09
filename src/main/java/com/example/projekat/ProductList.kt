package com.example.projekat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductList : AppCompatActivity() {

    companion object {
        private const val PRODUCT_DETAIL_REQUEST = 1
        private const val ADD_PRODUCT_REQUEST = 2
    }

    private lateinit var productList: MutableList<Product>
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        val name = intent.getStringExtra("name")
        val surname = intent.getStringExtra("surname")
        val email = intent.getStringExtra("email")
        val street = intent.getStringExtra("street")
        val country = intent.getStringExtra("country")

        if (name == null || surname == null || email == null || street == null || country == null) {
            Log.e("ProductList", "Nedostaju podaci iz MainActivity")
            finish() // Zatvori aktivnost ako podaci nedostaju
            return
        }

        Log.d("ProductList", "Primljeni podaci: $name, $surname, $email, $street, $country")

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val titleTextView: TextView = findViewById(R.id.titleTextView)
        val buttonAddProduct: Button = findViewById(R.id.buttonAddProduct)

        productList = mutableListOf(
            Product(1, "Proizvod 1", 100.0, 10, 2, 7, "Srbija"),
            Product(2, "Proizvod 2", 200.0, 5, 1, 5, "Hrvatska"),
            Product(3, "Proizvod 3", 300.0, 3, 3, 10, "Bosna i Hercegovina")
        )

        val resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val updatedProduct = data?.getSerializableExtra("product") as? Product
                updatedProduct?.let {
                    val index = productList.indexOfFirst { it.id == updatedProduct.id }
                    if (index != -1) {
                        productList[index] = updatedProduct
                        productAdapter.notifyItemChanged(index)
                    }
                }
            }
        }

        val addProductLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val newProduct = data?.getSerializableExtra("product") as? Product
                newProduct?.let {
                    productList.add(newProduct)
                    productAdapter.notifyItemInserted(productList.size - 1)
                }
            }
        }
        val cart = mutableListOf<Product>()
        productAdapter = ProductAdapter(productList,
            onClick = { product ->
                val intent = Intent(this@ProductList, ProductDetail::class.java).apply {
                    putExtra("product", product)
                }
                resultLauncher.launch(intent)
            },
            onAddToCartClick = { product ->
                val existingProduct = cart.find { it.id == product.id }
                if (existingProduct != null) {
                    existingProduct.quantity += 1
                } else {
                    cart.add(product.copy(quantity = 1))
                }
            }
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ProductList)
            adapter = productAdapter
        }

        buttonAddProduct.setOnClickListener {
            val intent = Intent(this@ProductList, AddProduct::class.java)
            addProductLauncher.launch(intent)
        }
    }

    inner class ProductAdapter(
        private val products: List<Product>,
        private val onClick: (Product) -> Unit,
        private val onAddToCartClick: (Product) -> Unit
    ) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

        inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textViewName: TextView = TextView(this@ProductList).apply { id = View.generateViewId() }
            val textViewPrice: TextView = TextView(this@ProductList).apply { id = View.generateViewId() }
            val buttonAddToCart: Button = Button(this@ProductList).apply { id = View.generateViewId(); text = "Dodaj u Korpu" }

            init {
                val productLayout = LinearLayout(this@ProductList).apply {
                    id = View.generateViewId()
                    orientation = LinearLayout.VERTICAL
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    addView(textViewName)
                    addView(textViewPrice)
                    addView(buttonAddToCart)
                }

                itemView.setOnClickListener {
                    onClick(products[adapterPosition])
                }

                buttonAddToCart.setOnClickListener {
                    onAddToCartClick(products[adapterPosition])
                }

                (itemView as LinearLayout).addView(productLayout)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val itemView = LinearLayout(this@ProductList).apply {
                id = View.generateViewId()
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }
            return ProductViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
            val product = products[position]
            holder.textViewName.text = "Naziv: ${product.name}"
            holder.textViewPrice.text = "Cena: ${product.price}"
        }

        override fun getItemCount() = products.size
    }

}
