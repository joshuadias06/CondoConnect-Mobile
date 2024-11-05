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
    private var produtos: MutableList<Produto> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem)

        setupRecyclerView()
        carregarProdutos()

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            startActivity(Intent(this, IncluirProdutoActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CustomAdapter(produtos, ::deletarProduto, ::editarProduto, ::abrirDetalhesProduto)
        recyclerView.adapter = adapter
    }

    private fun carregarProdutos() {
        val apiService = criarRetrofit().create(ApiService::class.java)

        apiService.getProdutos().enqueue(object : Callback<ProdutoResponse> {
            override fun onResponse(call: Call<ProdutoResponse>, response: Response<ProdutoResponse>) {
                if (response.isSuccessful) {
                    response.body()?.produtos?.let {
                        produtos.clear()
                        produtos.addAll(it)
                        adapter.notifyDataSetChanged()
                    } ?: run {
                        Log.e("API Error", "Nenhum produto encontrado.")
                    }
                } else {
                    Log.e("API Error", "Erro ao carregar produtos: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ProdutoResponse>, t: Throwable) {
                Log.e("API Failure", "Erro de rede.", t)
            }
        })
    }

    private fun abrirDetalhesProduto(produto: Produto) {
        val intent = Intent(this, ServiceDetailActivity::class.java).apply {
            putExtra("serviceName", produto.nome_produto)
            putExtra("servicePrice", "R$ ${produto.preco_produto}")
            putExtra("serviceDescription", produto.desc_produto)// Certifique-se que Produto tem um campo descrição
            putExtra("serviceImage", produto.imagem_produto) // Passando a URL da imagem
        }
        startActivity(intent)
    }

    private fun deletarProduto(produto: Produto) {
        val apiService = criarRetrofit().create(ApiService::class.java)

        apiService.deletarProduto(produto.id_produto).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    adapter.removeItem(produto)
                } else {
                    Log.e("Delete Error", "Erro ao deletar produto: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Delete Failure", "Erro de rede ao deletar produto.", t)
            }
        })
    }

    private fun editarProduto(produto: Produto) {
        val intent = Intent(this, EditarProdutoActivity::class.java).apply {
            putExtra("produto", produto)
        }
        startActivityForResult(intent, EDITAR_PRODUTO_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDITAR_PRODUTO_REQUEST && resultCode == RESULT_OK) {
            carregarProdutos()
        }
    }

    private fun criarRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://e50a8b2c-5876-4ac7-a5f9-f306e6306666-00-2jm1sibifrd8.spock.replit.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        private const val EDITAR_PRODUTO_REQUEST = 1
    }
}
