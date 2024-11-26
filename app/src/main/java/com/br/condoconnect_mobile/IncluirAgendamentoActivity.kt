package com.br.condoconnect_mobile

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IncluirAgendamentoActivity : AppCompatActivity() {

    private lateinit var editTextEvento: EditText
    private lateinit var editTextDescricao: EditText
    private lateinit var editTextData: EditText
    private lateinit var editTextHorario: EditText // Campo para a URL da imagem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incluir_agendamento)

        editTextEvento = findViewById(R.id.editTextEvento)
        editTextDescricao = findViewById(R.id.editTextDescricao)
        editTextData = findViewById(R.id.editTextData)
        editTextHorario = findViewById(R.id.editTextHorario)

        findViewById<Button>(R.id.buttonIncluir).setOnClickListener {
            incluirAgendamento()
        }
    }

    private fun incluirAgendamento() {
        val evento = editTextEvento.text.toString()
        val descricao = editTextDescricao.text.toString()
        val data = editTextData.text.toString()
        val horario = editTextHorario.text.toString()

        if (evento.isNotEmpty() && descricao.isNotEmpty() && data.isNotEmpty() && horario.isNotEmpty()) {
            val apiService = criarRetrofit().create(ApiService::class.java)

            apiService.incluirAgendamento(evento, descricao, data, "12:00") // Horário fixo como exemplo
                .enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            setResult(RESULT_OK)
                            finish()
                        } else {
                            Log.e("Incluir Error", "Erro ao incluir Agendamento: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.e("Incluir Failure", "Erro de rede ao incluir agendamento.", t)
                    }
                })
        } else {
            Toast.makeText(this, "Preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun criarRetrofit(): Retrofit {
        return Retrofit.Builder()
            //.baseUrl("https://357c4451-a805-4144-911a-3dbcda509679-00-1ma976m8koaut.kirk.replit.dev/") // URL correta
            .baseUrl("http://10.135.167.28/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
