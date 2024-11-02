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
    private lateinit var produtos: MutableList<Produto> // Armazena a lista de produtos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicializa a lista de produtos
        produtos = mutableListOf()

        // Carregar produtos inicialmente
        carregarProdutos()

        // Botão para adicionar novo produto
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            // Navegar para a tela de cadastro (ServiceDetailActivity)
            val intent = Intent(this, ServiceDetailActivity::class.java)
            startActivity(intent)
        }
    }

    private fun carregarProdutos() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://e50a8b2c-5876-4ac7-a5f9-f306e6306666-00-2jm1sibifrd8.spock.replit.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        // Chamar API para buscar produtos
        apiService.getProdutos().enqueue(object : Callback<ProdutoResponse> {
            override fun onResponse(call: Call<ProdutoResponse>, response: Response<ProdutoResponse>) {
                if (response.isSuccessful) {
                    produtos = response.body()?.produtos?.toMutableList() ?: mutableListOf()
                    adapter = CustomAdapter(produtos, ::deletarProduto, ::editarProduto) // Passa a função de edição
                    recyclerView.adapter = adapter
                } else {
                    Log.e("API Error", "Erro ao carregar produtos: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ProdutoResponse>, t: Throwable) {
                Log.e("API Failure", "Erro de rede.", t)
            }
        })
    }

    private fun deletarProduto(produto: Produto) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://e50a8b2c-5876-4ac7-a5f9-f306e6306666-00-2jm1sibifrd8.spock.replit.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.deletarProduto(produto.id_produto).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    adapter.removeItem(produto) // Remove o item do adapter
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
        // Navegar para a tela de edição
        val intent = Intent(this, EditarProdutoActivity::class.java)
        intent.putExtra("produto", produto)
        startActivityForResult(intent, EDITAR_PRODUTO_REQUEST) // Usar startActivityForResult
    }

    // Atualizar a lista após voltar da edição
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDITAR_PRODUTO_REQUEST && resultCode == RESULT_OK) {
            carregarProdutos() // Recarregar a lista de produtos
        }
    }

    companion object {
        private const val EDITAR_PRODUTO_REQUEST = 1 // Código de solicitação para edição
    }
}
