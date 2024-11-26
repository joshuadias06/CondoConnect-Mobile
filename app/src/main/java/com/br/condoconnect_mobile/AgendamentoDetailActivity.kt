package com.br.condoconnect_mobile

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class AgendamentoDetailActivity : AppCompatActivity() {

    private lateinit var serviceEventTextView: TextView
    private lateinit var serviceDateTextView: TextView
    private lateinit var serviceDescriptionTextView: TextView
    private lateinit var serviceHorarioView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_detail)

        // Receber os dados da intent
        val serviceEvent = intent.getStringExtra("serviceEvent") ?: "Evento não disponível"
        val serviceDate = intent.getStringExtra("serviceDate") ?: "Data não disponível"
        val serviceDescription = intent.getStringExtra("serviceDescription") ?: "Descrição não disponível"
        val serviceHorario = intent.getStringExtra("serviceHorario")

        // Inicializar os TextViews e ImageView
        serviceEventTextView = findViewById(R.id.serviceEvent)
        serviceDateTextView = findViewById(R.id.serviceDate)
        serviceDescriptionTextView = findViewById(R.id.serviceDescription)
        serviceHorarioView = findViewById(R.id.horarioView) // Inicializar o ImageView

        // Configurar os campos da activity
        serviceEventTextView.text = serviceEvent
        serviceDateTextView.text = serviceDate
        serviceDescriptionTextView.text = serviceDescription

        // Carregar a imagem usando o Picasso
        serviceHorario?.let {
            Picasso.get().load(it).into(serviceHorarioView) // Carregar a imagem no ImageView
        }
    }
}
