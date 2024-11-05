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

class IncluirProdutoActivity : AppCompatActivity() {

    private lateinit var editTextNome: EditText
    private lateinit var editTextDescricao: EditText
    private lateinit var editTextPreco: EditText
    private lateinit var editTextImagem: EditText // Campo para a URL da imagem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incluir_produto)

        editTextNome = findViewById(R.id.editTextNome)
        editTextDescricao = findViewById(R.id.editTextDescricao)
        editTextPreco = findViewById(R.id.editTextPreco)
        editTextImagem = findViewById(R.id.editTextImagem) // Inicializa o EditText para a imagem

        findViewById<Button>(R.id.buttonIncluir).setOnClickListener {
            incluirProduto()
        }
    }

    private fun incluirProduto() {
        val nome = editTextNome.text.toString()
        val descricao = editTextDescricao.text.toString()
        val preco = editTextPreco.text.toString().toDoubleOrNull()
        val imagem = editTextImagem.text.toString() // Captura a URL da imagem

        if (nome.isNotEmpty() && descricao.isNotEmpty() && preco != null && imagem.isNotEmpty()) {
            val precoString = preco.toString()
            val apiService = criarRetrofit().create(ApiService::class.java)

            apiService.incluirProduto(nome, descricao, precoString, imagem).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        setResult(RESULT_OK) // Retorna um resultado de sucesso
                        finish() // Fecha a Activity
                    } else {
                        Log.e("Incluir Error", "Erro ao incluir produto: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("Incluir Failure", "Erro de rede ao incluir produto.", t)
                }
            })
        } else {
            Toast.makeText(this, "Preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun criarRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://919bc248-e514-4e60-a1d1-e9924f95d096-00-srq164nqs0ci.riker.replit.dev/") // URL base corrigida
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
