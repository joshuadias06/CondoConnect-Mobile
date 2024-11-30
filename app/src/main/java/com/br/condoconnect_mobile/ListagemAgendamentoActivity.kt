package com.br.condoconnect_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListagemAgendamentoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapterAgendamento
    private var agendamentos: MutableList<Agendamento> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem_agendamento)

        // Configuração do listener para o botão 'imgCarrinho'
        val imgCarrinho: ImageView = findViewById(R.id.imageView4)
        imgCarrinho.setOnClickListener {
            // Criando o Intent para abrir a ListagemActivity
            val intent = Intent(this, ListagemActivity::class.java)
            startActivity(intent)
        }

        // Configuração do listener para o botão 'imgConfig'
        val imgConfig: ImageView = findViewById(R.id.imageView2)
        imgConfig.setOnClickListener {
            // Criando o Intent para abrir a ConfigActivity
            val intent = Intent(this, ConfigActivity::class.java)
            startActivity(intent)
        }

        // Configuração do listener para o botão 'imgCarrinho'
        val imgInicio: ImageView = findViewById(R.id.imageView6)
        imgInicio.setOnClickListener {
            // Criando o Intent para abrir a ListagemActivity
            val intent = Intent(this, StartActivity2::class.java)
            startActivity(intent)
        }

        setupRecyclerView()
        carregarAgendamentos()

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            startActivity(Intent(this, IncluirAgendamentoActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CustomAdapterAgendamento(agendamentos, ::removerAgendamento, ::editarAgendamento, ::abrirDetalhesAgendamento)
        recyclerView.adapter = adapter
    }

    private fun carregarAgendamentos() {
        val apiService = criarRetrofit().create(ApiService::class.java)

        apiService.getAgendamentos().enqueue(object : Callback<List<Agendamento>> { // Modificado para lista
            override fun onResponse(call: Call<List<Agendamento>>, response: Response<List<Agendamento>>) {
                if (response.isSuccessful) {
                    response.body()?.let { lista ->
                        adapter.updateAgendamentos(lista.toMutableList())
                    } ?: Log.e("API Error", "Nenhum agendamento encontrado.")
                } else {
                    Log.e("API Error", "Erro ao carregar agendamentos: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Agendamento>>, t: Throwable) {
                Log.e("API Failure", "Erro de rede.", t)
            }
        })
    }

    private fun criarRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.15.11/")  // URL correta
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun abrirDetalhesAgendamento(agendamento: Agendamento) {
        val intent = Intent(this, AgendamentoDetailActivity::class.java).apply {
            putExtra("serviceEvent", agendamento.evento)
            putExtra("serviceDate", agendamento.data)
            putExtra("serviceDescription", agendamento.descricao)
        }
        startActivity(intent)
    }

    private fun removerAgendamento(agendamento: Agendamento) {
        val apiService = criarRetrofit().create(ApiService::class.java)

        apiService.removerAgendamento(agendamento.id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    adapter.removeItem(agendamento)
                } else {
                    Log.e("Delete Error", "Erro ao deletar agendamento: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Delete Failure", "Erro de rede ao deletar agendamento.", t)
            }
        })
    }

    private fun editarAgendamento(agendamento: Agendamento) {
        val intent = Intent(this, EditarAgendamentoActivity::class.java).apply {
            putExtra("agendamento", agendamento)
        }
        startActivityForResult(intent, EDITAR_AGENDAMENTO_REQUEST)
    }

    companion object {
        private const val EDITAR_AGENDAMENTO_REQUEST = 1
    }
}
