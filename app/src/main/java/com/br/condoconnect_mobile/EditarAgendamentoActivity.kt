package com.br.condoconnect_mobile

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditarAgendamentoActivity : AppCompatActivity() {

    private lateinit var evento: EditText
    private lateinit var data: EditText
    private lateinit var descricao: EditText
    private lateinit var horario: EditText
    private lateinit var editarAgendamentoButton: Button
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_agendamento)

        // Inicializar views
        evento = findViewById(R.id.evento)
        data = findViewById(R.id.data)
        descricao = findViewById(R.id.descricao)
        horario = findViewById(R.id.horario)
        editarAgendamentoButton = findViewById(R.id.editarAgendamentoButton)

        // Obter o agendamento da Intent
        intent.getParcelableExtra<Agendamento>("agendamento")?.let { agendamento ->
            id = agendamento.id
            evento.setText(agendamento.evento)
            data.setText(agendamento.data)
            descricao.setText(agendamento.descricao)
            horario.setText(agendamento.horario)
        }

        // Configurar o clique do botão para editar
        editarAgendamentoButton.setOnClickListener {
            if (validarCampos()) {
                editarAgendamento()
            }
        }
    }

    // Método para validar campos de entrada
    private fun validarCampos(): Boolean {
        val evento = evento.text.toString()
        val data = data.text.toString()
        val descricao = descricao.text.toString()
        val horario = horario.text.toString()

        return when {
            evento.isEmpty() -> {
                mostrarMensagem("O nome do evento é obrigatório.")
                false
            }
            data.isEmpty() -> {
                mostrarMensagem("A data do agendamento é obrigatória.")
                false
            }
            descricao.isEmpty() -> {
                mostrarMensagem("A descrição do agendamento é obrigatória.")
                false
            }
            horario.isEmpty() -> {
                mostrarMensagem("O horário é obrigatório.")
                false
            }
            else -> true
        }
    }

    // Método para mostrar mensagens de Toast
    private fun mostrarMensagem(mensagem: String) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }

    private fun editarAgendamento() {
        val evento = evento.text.toString()
        val data = data.text.toString()
        val descricao = descricao.text.toString()
        val horario = horario.text.toString()

        // Configurar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.135.167.28/") // Ajuste o IP conforme sua rede
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        // Chamar API para editar agendamento em uma Coroutine
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.editarAgendamento(id, evento, descricao, data, horario).execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        response.body()?.let { resposta ->
                            mostrarMensagem(resposta.status)
                            setResult(RESULT_OK)
                            finish()
                        } ?: mostrarMensagem("Agendamento editado, mas sem resposta do servidor.")
                    } else {
                        mostrarMensagem("Erro ao editar: ${response.code()} - ${response.message()}")
                    }
                }
            } catch (t: Throwable) {
                Log.e("Edit Failure", "Erro de rede ao editar agendamento: ${t.message}", t)
                withContext(Dispatchers.Main) {
                    mostrarMensagem("Erro de rede: ${t.message}")
                }
            }
        }
    }
}
