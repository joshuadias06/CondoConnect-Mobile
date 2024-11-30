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
        setContentView(R.layout.activity_agendamento_detail)

        // Receber os dados da intent
        val serviceEvent = intent.getStringExtra("serviceEvent") ?: "Evento não disponível"
        val serviceDate = intent.getStringExtra("serviceDate") ?: "Data não disponível"
        val serviceDescription = intent.getStringExtra("serviceDescription") ?: "Descrição não disponível"

        // Inicializar os TextViews e ImageView
        serviceEventTextView = findViewById(R.id.serviceEvent)
        serviceDateTextView = findViewById(R.id.serviceDate)
        serviceDescriptionTextView = findViewById(R.id.serviceDescription)

        // Configurar os campos da activity
        serviceEventTextView.text = serviceEvent
        serviceDateTextView.text = serviceDate
        serviceDescriptionTextView.text = serviceDescription

    }
}
