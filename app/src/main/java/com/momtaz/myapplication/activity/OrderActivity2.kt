package com.momtaz.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.momtaz.myapplication.adapter.RecyclerViewAdapter
import com.momtaz.myapplication.data.Order
import com.momtaz.myapplication.databinding.ActivityOrder2Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OrderActivity2 : AppCompatActivity() , RecyclerViewAdapter.OnItemClickListener{
    private lateinit var adapterOder: RecyclerViewAdapter
    var orders: List<Order> = listOf()
    private lateinit var binding: ActivityOrder2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrder2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ProgressBar.visibility = View.VISIBLE
        binding.RvOrder.apply {
            layoutManager = LinearLayoutManager(applicationContext)
        }
        adapterOder = RecyclerViewAdapter(orders,this)

        binding.RvOrder.adapter = adapterOder

        CoroutineScope(Dispatchers.Main).launch {
            val fetchedOrders = getAllOrders()
            orders = fetchedOrders
            orders.sortedBy {
                it.date
            }
            adapterOder = RecyclerViewAdapter(orders,this@OrderActivity2)
            binding.RvOrder.adapter = adapterOder
            adapterOder
        }


    }
    private suspend fun getAllOrders(): List<Order> {
        val db = FirebaseFirestore.getInstance()
        val allOrders = mutableListOf<Order>()

        try {
            // Get a reference to the users collection
            val usersCollection = db.collection("user")

            // Get all users
            val usersSnapshot = usersCollection.get().await()

            for (userDocument in usersSnapshot.documents) {
                Log.d("FirestoreDebug", "User ID: ${userDocument.id}")

                // Get the orders subcollection for each user
                val ordersCollection = userDocument.reference.collection("orders")
                val ordersSnapshot = ordersCollection.get().await()

                for (orderDocument in ordersSnapshot.documents) {
                    Log.d("FirestoreDebug", "Order ID: ${orderDocument.id}")

                    // Convert each document to an Order object
                    val order = orderDocument.toObject(Order::class.java)
                    if (order != null) {
                        Log.d("FirestoreDebug", "Order: $order")
                        allOrders.add(order)
                    } else {
                        Log.d("FirestoreDebug", "Order document is null or invalid")
                    }
                }
            }
            binding.ProgressBar.visibility =View.GONE
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return allOrders
    }

    override fun onItemClick(order: Order) {
        val intent = Intent(this,OderDetailsActivity::class.java)
        intent.putExtra("order",order)
        startActivity(intent)
    }
}


