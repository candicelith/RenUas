package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.database.Cart
import com.example.myapplication.database.CartDao
import com.example.myapplication.database.CartRoomDatabase
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.Supplies
import com.example.myapplication.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mCartDao: CartDao
    private lateinit var executorService: ExecutorService
    private var isCart = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        executorService = Executors.newSingleThreadExecutor()
        val db = CartRoomDatabase.getDatabase(this)
        mCartDao = db!!.cartDao()!!

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvSupplies.layoutManager = LinearLayoutManager(this)

        val client = ApiClient.getInstance()
        val responseSupplies = client.getAllSupplies()

        responseSupplies.enqueue(object: Callback<List<Supplies>> {
            override fun onResponse(call: Call<List<Supplies>>, response: Response<List<Supplies>>) {
                if (response.isSuccessful && response.body()!! != null) {
                    val suppliesList = response.body()!!

                    mCartDao.allCart.observe(this@MainActivity) {
                        cartList ->
                        val cartSupplies = cartList.map { it.suppliesName }.toSet()

                        val adapter = SuppliesAdapter(suppliesList, cartSupplies) {
                            suppliesItem, isCart ->
                            handleCart(suppliesItem, isCart)
                        }
                        binding.rvSupplies.adapter = adapter
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Failed to Load", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(p0: Call<List<Supplies>>, p1: Throwable) {
                Toast.makeText(this@MainActivity, "Koneksi Error", Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnCarts.setOnClickListener {
            val intentToCart = Intent(this, CartActivity::class.java)
            startActivity(intentToCart)
        }
    }

    private fun handleCart(suppliesItem: String, isCart: Boolean) {
        executorService.execute {
            if (isCart) {
                mCartDao.insert(Cart(suppliesName = suppliesItem))
            } else {
                mCartDao.deleteBySuppliesName(suppliesItem)
            }
        }
    }
}