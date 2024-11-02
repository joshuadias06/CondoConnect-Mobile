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

class EditarProdutoActivity : AppCompatActivity() {

    private lateinit var nomeProduto: EditText
    private lateinit var precoProduto: EditText
    private lateinit var descricaoProduto: EditText
    private lateinit var editarProdutoButton: Button
    private var produtoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_produto)

        nomeProduto = findViewById(R.id.nomeProduto)
        precoProduto = findViewById(R.id.precoProduto)
        descricaoProduto = findViewById(R.id.descricaoProduto)
        editarProdutoButton = findViewById(R.id.editarProdutoButton)

        // Obter o produto da Intent
        val produto = intent.getParcelableExtra<Produto>("produto")
        produto?.let {
            produtoId = it.id_produto
            nomeProduto.setText(it.nome_produto)
            precoProduto.setText(it.preco_produto.toString())
            descricaoProduto.setText(it.desc_produto)
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
                Toast.makeText(this, "O nome do produto é obrigatório.", Toast.LENGTH_SHORT).show()
                false
            }
            preco.isEmpty() -> {
                Toast.makeText(this, "O preço do produto é obrigatório.", Toast.LENGTH_SHORT).show()
                false
            }
            descricao.isEmpty() -> {
                Toast.makeText(this, "A descrição do produto é obrigatória.", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun editarProduto() {
        val nome = nomeProduto.text.toString()
        val preco = precoProduto.text.toString()
        val descricao = descricaoProduto.text.toString()

        // Configurar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://e50a8b2c-5876-4ac7-a5f9-f306e6306666-00-2jm1sibifrd8.spock.replit.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        // Chamar API para editar produto
        apiService.editarProduto(produtoId, nome, descricao, preco, "").enqueue(object : Callback<RespostaEdit> {
            override fun onResponse(call: Call<RespostaEdit>, response: Response<RespostaEdit>) {
                if (response.isSuccessful) {
                    val resposta = response.body()
                    resposta?.let {
                        Toast.makeText(this@EditarProdutoActivity, it.status, Toast.LENGTH_SHORT).show()
                        // Produto editado com sucesso, retorna resultado para a ListagemActivity
                        setResult(RESULT_OK) // Define o resultado como OK
                        finish() // Voltar para a tela anterior
                    }
                } else {
                    Log.e("Edit Error", "Erro ao editar produto: ${response.message()}")
                    Toast.makeText(this@EditarProdutoActivity, "Erro ao editar produto: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RespostaEdit>, t: Throwable) {
                Log.e("Edit Failure", "Erro de rede ao editar produto.", t)
                Toast.makeText(this@EditarProdutoActivity, "Erro de rede.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
