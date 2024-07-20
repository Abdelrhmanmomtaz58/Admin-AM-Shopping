package com.momtaz.myapplication.activity

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.momtaz.myapplication.R
import com.momtaz.myapplication.adapter.CartOrderProductAdapter
import com.momtaz.myapplication.data.CartProduct
import com.momtaz.myapplication.data.Order
import com.momtaz.myapplication.databinding.ActivityOderDetailsBinding

class OderDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOderDetailsBinding
    private lateinit var adapterCart: CartOrderProductAdapter
    private lateinit var updateStatus: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val order: Order? = intent.getParcelableExtra("order")
        if (order != null) {
            binding.apply {
                tvOrderId.text = "# ${order.orderId}"
                tvOrderStatus.text = order.orderStatus
                tvFullName.text = order.address.fullName
                tvAddress.text = order.address.state + " " + order.address.city
                tvPhoneNumber.text = order.address.phone
                tvTotalPrice.text = order.totalPrice.toString()
                if (order.orderStatus == "Ordered") {
                    imageOrderState.setImageResource(R.color.g_orange_yellow)
                } else {
                    imageOrderState.setImageResource(R.color.g_green)

                }

            }


        }
        if (order != null) {
            setupRecyclerView(order.products)
            editOrderStatus(order)
        }

    }

    private fun editOrderStatus(order: Order) {
        binding.edit.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Edit order status")
            val customLayout: View = layoutInflater.inflate(R.layout.custom_alert_dialog, null)
            builder.setView(customLayout)
            builder.setPositiveButton("Ok") { dialog: DialogInterface?, which: Int ->
                val editText: EditText = customLayout.findViewById(R.id.edStatus)
                if (editText.text != null) {
                    updateStatus = editText.text.toString()
                    updateStatus(updateStatus, order)
                } else {
                    Toast.makeText(this, "Enter new Status", Toast.LENGTH_LONG).show()
                }
            }
            val dialog = builder.create()
            dialog.show()
        }

    }

    private fun updateStatus(updateStatus: String, order: Order) {
        val db = FirebaseFirestore.getInstance()
        val oderRef = db.collection("user").document(order.userid).collection("orders")
        oderRef.whereEqualTo("orderId", order.orderId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    for (document in querySnapshot.documents) {
                        oderRef.document(document.id).update("orderStatus", updateStatus)
                            .addOnSuccessListener {
                                binding.tvOrderStatus.text = updateStatus
                            }.addOnFailureListener {
                                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                            }
                    }
                } else {
                    Toast.makeText(this, "No matching order found.", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
    }


    private fun setupRecyclerView(products: List<CartProduct>) {
        adapterCart = CartOrderProductAdapter(products, this)

        binding.rvProducts.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterCart

        }
    }
}