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

class EditarProdutoActivity : AppCompatActivity() {

    private lateinit var nomeProduto: EditText
    private lateinit var precoProduto: EditText
    private lateinit var descricaoProduto: EditText
    private lateinit var editarProdutoButton: Button
    private var produtoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_produto)

        // Inicializar views
        nomeProduto = findViewById(R.id.nomeProduto)
        precoProduto = findViewById(R.id.precoProduto)
        descricaoProduto = findViewById(R.id.descricaoProduto)
        editarProdutoButton = findViewById(R.id.editarProdutoButton)

        // Obter o produto da Intent
        intent.getParcelableExtra<Produto>("produto")?.let { produto ->
            produtoId = produto.id_produto
            nomeProduto.setText(produto.nome_produto)
            precoProduto.setText(produto.preco_produto.toString())
            descricaoProduto.setText(produto.desc_produto)
        }

        // Configurar o clique do botão para editar
        editarProdutoButton.setOnClickListener {
            if (validarCampos()) {
                editarProduto()
            }
        }
    }

    // Método para validar campos de entrada
    private fun validarCampos(): Boolean {
        val nome = nomeProduto.text.toString()
        val preco = precoProduto.text.toString()
        val descricao = descricaoProduto.text.toString()

        return when {
            nome.isEmpty() -> {
                mostrarMensagem("O nome do produto é obrigatório.")
                false
            }
            preco.isEmpty() -> {
                mostrarMensagem("O preço do produto é obrigatório.")
                false
            }
            descricao.isEmpty() -> {
                mostrarMensagem("A descrição do produto é obrigatória.")
                false
            }
            !preco.isDouble() -> {
                mostrarMensagem("O preço deve ser um número válido.")
                false
            }
            else -> true
        }
    }

    // Método para mostrar mensagens de Toast
    private fun mostrarMensagem(mensagem: String) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }

    // Função de extensão para verificar se uma String é um número válido
    private fun String.isDouble(): Boolean {
        return try {
            this.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    private fun editarProduto() {
        val nome = nomeProduto.text.toString()
        val preco = precoProduto.text.toString()
        val descricao = descricaoProduto.text.toString()

        // Configurar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://919bc248-e514-4e60-a1d1-e9924f95d096-00-srq164nqs0ci.riker.replit.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        // Chamar API para editar produto em uma Coroutine
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.editarProduto(produtoId, nome, descricao, preco, "").execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val resposta = response.body()
                        Log.d("API Response", resposta.toString())
                        resposta?.let {
                            mostrarMensagem(it.status)
                            setResult(RESULT_OK)
                            finish()
                        }
                    } else {
                        Log.e("Edit Error", "Erro ao editar produto: ${response.message()}")
                        mostrarMensagem("Erro ao editar produto: ${response.message()}")
                    }
                }
            } catch (t: Throwable) {
                Log.e("Edit Failure", "Erro de rede ao editar produto: ${t.message}", t)
                withContext(Dispatchers.Main) {
                    mostrarMensagem("Erro de rede: ${t.message}")
                }
            }
        }
    }
}
