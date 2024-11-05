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
    private var produtos: MutableList<Produto> = mutableListOf() // Armazena a lista de produtos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem)

        setupRecyclerView()
        carregarProdutos()

        // Botão para adicionar novo produto
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            // Navegar para a tela de inclusão de produto
            startActivity(Intent(this, IncluirProdutoActivity::class.java)) // Modificado para abrir IncluirProdutoActivity
        }
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CustomAdapter(produtos, ::deletarProduto, ::editarProduto) // Passa a função de edição
        recyclerView.adapter = adapter
    }

    private fun carregarProdutos() {
        val apiService = criarRetrofit().create(ApiService::class.java)

        // Chamar API para buscar produtos
        apiService.getProdutos().enqueue(object : Callback<ProdutoResponse> {
            override fun onResponse(call: Call<ProdutoResponse>, response: Response<ProdutoResponse>) {
                if (response.isSuccessful) {
                    response.body()?.produtos?.let {
                        produtos.clear()
                        produtos.addAll(it)
                        adapter.notifyDataSetChanged() // Notifica o adapter sobre mudanças na lista
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

    private fun deletarProduto(produto: Produto) {
        val apiService = criarRetrofit().create(ApiService::class.java)

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
        val intent = Intent(this, EditarProdutoActivity::class.java).apply {
            putExtra("produto", produto)
        }
        startActivityForResult(intent, EDITAR_PRODUTO_REQUEST) // Usar startActivityForResult
    }

    // Atualizar a lista após voltar da edição
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDITAR_PRODUTO_REQUEST && resultCode == RESULT_OK) {
            carregarProdutos() // Recarregar a lista de produtos
        }
    }

    private fun criarRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://e50a8b2c-5876-4ac7-a5f9-f306e6306666-00-2jm1sibifrd8.spock.replit.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        private const val EDITAR_PRODUTO_REQUEST = 1 // Código de solicitação para edição
    }
}
