package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.Supplies

class SuppliesAdapter(
    private val supplies: List<Supplies>,
    private val cartSupplies: Set<String>,
    private val onCartClicked: (String, Boolean) -> Unit
) : RecyclerView.Adapter<SuppliesAdapter.SuppliesViewHolder>() {

    private val cartItem = cartSupplies.toMutableSet()

    class SuppliesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.txt_supplies_name)
        val textColor: TextView = itemView.findViewById(R.id.txt_supplies_color)
        val textPrice: TextView = itemView.findViewById(R.id.txt_supplies_price)
        val cartIcon: ImageView = itemView.findViewById(R.id.icon_cart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuppliesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_supplies, parent, false
        )
        return SuppliesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SuppliesViewHolder, position: Int) {
        val suppliesItem = supplies[position]

        val supply = suppliesItem.jenis
        val color = suppliesItem.warna
        val price = suppliesItem.harga

        holder.textName.text = supply
        holder.textColor.text = color
        holder.textPrice.text = price

        val supplyKey = "$supply \n $color"

        val isCart = cartItem.contains(supplyKey)
        holder.cartIcon.setImageResource(
            if (isCart) {
                R.drawable.baseline_shopping_cart_24_1
            } else {
                R.drawable.baseline_add_shopping_cart_24
            }
        )

        holder.cartIcon.setOnClickListener {
            val newCartState = !isCart
            onCartClicked(supplyKey, newCartState)
        }
    }

    override fun getItemCount(): Int {
        return supplies.size
    }

}