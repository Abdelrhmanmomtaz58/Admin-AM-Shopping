package com.momtaz.myapplication.adapter

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.momtaz.myapplication.R
import com.momtaz.myapplication.data.CartProduct

class CartOrderProductAdapter(private val cartOrderProduct:List<CartProduct>,val context: Context):RecyclerView.Adapter<CartOrderProductAdapter.CartOrderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartOrderViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cart_order_prodect_item,parent,false)
        return CartOrderViewHolder(itemView)
    }

    override fun getItemCount(): Int = cartOrderProduct.size

    override fun onBindViewHolder(holder: CartOrderViewHolder, position: Int) {
        val currentItem = cartOrderProduct[position]
        holder.orderProductName.text = currentItem.product.name
        Glide.with(context)
            .load(currentItem.product.images[0])
            .into(holder.imageOrder)
        if (currentItem.selectedColor!=null)
        {
            holder.imageColorOder.setImageDrawable(ColorDrawable(currentItem.selectedColor))
        }else{
            holder.imageColorOder.visibility= View.INVISIBLE

        }
        if (currentItem.selectedSize==null){
            holder.imageSize.visibility=View.INVISIBLE
        }else{
            holder.oderProductSize.text = currentItem.selectedSize
        }
        holder.productPrice.text =currentItem.product.price.toString()
        holder.productQuantity.text=currentItem.quantity.toString()

    }

    class CartOrderViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val orderProductName:TextView = itemView.findViewById(R.id.tvProductCartName)
        val imageOrder:ImageView = itemView.findViewById(R.id.imageCartProduct)
        val imageColorOder:ImageView =itemView.findViewById(R.id.imageCartProductColor)
        val oderProductSize:TextView = itemView.findViewById(R.id.tvCartProductSize)
        val productPrice:TextView = itemView.findViewById(R.id.tvProductCartPrice)
        val productQuantity:TextView = itemView.findViewById(R.id.tvBillingProductQuantity)
        val imageSize:ImageView = itemView.findViewById(R.id.imageCartProductSize)
    }
}