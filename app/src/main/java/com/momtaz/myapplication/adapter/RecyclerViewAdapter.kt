package com.momtaz.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.momtaz.myapplication.R
import com.momtaz.myapplication.data.Order

class RecyclerViewAdapter(private val orders : List<Order>,private val onItemClickListener: OnItemClickListener)
    :RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(order: Order)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.order_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = orders[position]
        holder.orderId.text = currentItem.orderId.toString()
        holder.orderDate.text = currentItem.date
        if (currentItem.orderStatus=="Ordered"){
            holder.orderStatus.setImageResource(R.color.g_orange_yellow)
        }else{
            holder.orderStatus.setImageResource(R.color.g_green)
        }
        holder.bind(currentItem,onItemClickListener)
    }


    class ViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView) {
        val orderId: TextView = itemView.findViewById(R.id.tvOrderId)
        val orderDate :TextView = itemView.findViewById(R.id.tvOrderDate)
        val orderStatus:ImageView = itemView.findViewById(R.id.imageOrderState)

        fun bind(order: Order, clickListener: OnItemClickListener) {
            itemView.setOnClickListener {
                clickListener.onItemClick(order)
            }
        }

    }
}