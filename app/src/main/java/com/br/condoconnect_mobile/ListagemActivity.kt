package com.br.condoconnect_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListagemActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configurar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://0f7e7338-f3e2-4548-8fd6-9b9d1750998c-00-15f6q8paief7s.kirk.replit.dev/")  // Trocar pelo seu IP local
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        // Chamar API para buscar produtos
        apiService.getProdutos().enqueue(object : Callback<List<Produto>> {
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                if (response.isSuccessful) {
                    val produtos = response.body() ?: emptyList()
                    adapter = CustomAdapter(produtos)
                    recyclerView.adapter = adapter
                } else {
                    Log.e("API Error", "Erro ao carregar produtos.")
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                Log.e("API Failure", "Erro de rede.", t)
            }
        })

        // Botão para adicionar novo produto
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            // Navegar para a tela de cadastro (ServiceDetailActivity)
            val intent = Intent(this, ServiceDetailActivity::class.java)
            startActivity(intent)
        }
    }
}