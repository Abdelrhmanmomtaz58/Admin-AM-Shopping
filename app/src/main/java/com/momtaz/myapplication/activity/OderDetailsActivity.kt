package com.momtaz.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.momtaz.myapplication.R
import com.momtaz.myapplication.adapter.CartOrderProductAdapter
import com.momtaz.myapplication.data.CartProduct
import com.momtaz.myapplication.data.Order
import com.momtaz.myapplication.databinding.ActivityOderDetailsBinding

class OderDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOderDetailsBinding
    private lateinit var adapterCart: CartOrderProductAdapter
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
            val cartProduct: List<CartProduct> = order.products


        }
        if (order != null) {
            setupRecyclerView(order.products)
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