package com.br.condoconnect_mobile

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ServiceDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_detail)


        val serviceName = intent.getStringExtra("serviceName") ?: "Nome não disponível"
        val servicePrice = intent.getStringExtra("servicePrice") ?: "Preço não disponível"

        // Referenciar os elementos do layout
        val serviceNameTextView: TextView = findViewById(R.id.serviceName)
        val servicePriceTextView: TextView = findViewById(R.id.servicePrice)

        serviceNameTextView.text = serviceName
        servicePriceTextView.text = servicePrice
    }
}