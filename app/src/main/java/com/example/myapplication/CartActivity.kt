package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.database.CartDao
import com.example.myapplication.database.CartRoomDatabase
import com.example.myapplication.databinding.ActivityCartBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CartActivity : AppCompatActivity() {

    private lateinit var mCartDao: CartDao
    private lateinit var executorService: ExecutorService
    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor()
        val db = CartRoomDatabase.getDatabase(this)
        mCartDao = db!!.cartDao()!!

        binding.btnBack.setOnClickListener {
            onNavigateUp()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        getAllCart()
    }

    private fun getAllCart() {
        mCartDao.allCart.observe(this) {
            cart ->

            val cartSuppliesItem = cart.map { it.suppliesName }

            val adapter: ArrayAdapter<String> =
                ArrayAdapter(this, android.R.layout.simple_list_item_1, cartSuppliesItem)

            binding.lvCart.adapter = adapter
        }
    }
}