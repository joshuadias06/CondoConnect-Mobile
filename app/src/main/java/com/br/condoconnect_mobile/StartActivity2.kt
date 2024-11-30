package com.br.condoconnect_mobile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageButton
import android.widget.ImageView

class StartActivity2 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_start2)

        // Configuração do listener para o botão 'btnCarrinhoLogin'
        val btnCarrinhoLogin: ImageButton = findViewById(R.id.carrinhoLogin2)

        btnCarrinhoLogin.setOnClickListener {
            // Criando o Intent para abrir a LoginActivity
            val intent = Intent(this, ListagemActivity::class.java)
            startActivity(intent)
        }

        // Configuração do listener para o botão 'imgCarrinho'
        val imgCarrinho: ImageView = findViewById(R.id.carrinho2)
        imgCarrinho.setOnClickListener {
            // Criando o Intent para abrir a LoginActivity
            val intent = Intent(this, ListagemActivity::class.java)
            startActivity(intent)
        }

        // Configuração do listener para o botão 'imgAgendar'
        val imgAgendar: ImageView = findViewById(R.id.agendar2)
        imgAgendar.setOnClickListener {
            // Criando o Intent para abrir a LoginActivity
            val intent = Intent(this, ListagemAgendamentoActivity::class.java)
            startActivity(intent)
        }

        // Configuração do listener para o botão 'imgConfig'
        val imgConfig: ImageView = findViewById(R.id.config2)
        imgConfig.setOnClickListener {
            // Criando o Intent para abrir a LoginActivity
            val intent = Intent(this, ConfigActivity::class.java)
            startActivity(intent)
        }

        val contatos: Button = findViewById(R.id.button2)
        contatos.setOnClickListener {
            // Criando o Intent para abrir a LoginActivity
            val intent = Intent(this, ContatoActivity::class.java)
            startActivity(intent)
        }

        // Configuração do padding para Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

