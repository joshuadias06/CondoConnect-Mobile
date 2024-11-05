package com.br.condoconnect_mobile

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class ServiceDetailActivity : AppCompatActivity() {

    private lateinit var serviceNameTextView: TextView
    private lateinit var servicePriceTextView: TextView
    private lateinit var serviceDescriptionTextView: TextView
    private lateinit var serviceImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_detail)

        // Receber os dados da intent
        val serviceName = intent.getStringExtra("serviceName") ?: "Nome não disponível"
        val servicePrice = intent.getStringExtra("servicePrice") ?: "Preço não disponível"
        val serviceDescription = intent.getStringExtra("serviceDescription") ?: "Descrição não disponível"
        val serviceImage = intent.getStringExtra("serviceImage") // Receber a URL da imagem

        // Inicializar os TextViews e ImageView
        serviceNameTextView = findViewById(R.id.serviceName)
        servicePriceTextView = findViewById(R.id.servicePrice)
        serviceDescriptionTextView = findViewById(R.id.serviceDescription)
        serviceImageView = findViewById(R.id.imageView) // Inicializar o ImageView

        // Configurar os campos da activity
        serviceNameTextView.text = serviceName
        servicePriceTextView.text = servicePrice
        serviceDescriptionTextView.text = serviceDescription

        // Carregar a imagem usando o Picasso
        serviceImage?.let {
            Picasso.get().load(it).into(serviceImageView) // Carregar a imagem no ImageView
        }
    }
}
