package com.br.condoconnect_mobile

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageButton
import android.widget.ImageView

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_start)

        // Configuração do listener para o botão 'carrinhoLogin'
        val carrinhoLogin: ImageButton = findViewById(R.id.carrinhoLogin)

        carrinhoLogin.setOnClickListener {
            // Criando o Intent para abrir a LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        // Configuração do listener para o botão 'carrinho'
        val carrinho: ImageView = findViewById(R.id.carrinho)
        carrinho.setOnClickListener {
            // Criando o Intent para abrir a LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

// Configuração do listener para o botão 'agendar'
        val agendar: ImageView = findViewById(R.id.agendar)
        agendar.setOnClickListener {
            // Criando o Intent para abrir a LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

// Configuração do listener para o botão 'config'
        val config: ImageView = findViewById(R.id.config)
        config.setOnClickListener {
            // Criando o Intent para abrir a LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
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
