import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.br.condoconnect_mobile.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener {
            val nome = binding.editTextNome.text.toString()
            val email = binding.editTextTextEmailAddress.text.toString()
            val senha = binding.editTextPassword.text.toString()
            val cpf = binding.editTextText2.text.toString()

            if (nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty() && cpf.isNotEmpty()) {
                val usuario = Usuario(nome, email, senha, cpf)
                cadastrarUsuario(usuario)
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cadastrarUsuario(usuario: Usuario) {
        RetrofitClient.apiService.cadastrarUsuario(usuario).enqueue(object : Callback<ResponseCadastro> {
            override fun onResponse(call: Call<ResponseCadastro>, response: Response<ResponseCadastro>) {
                if (response.isSuccessful) {
                    val resposta = response.body()
                    if (resposta?.success != null) {
                        Toast.makeText(this@RegisterActivity, resposta.success, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@RegisterActivity, resposta?.error ?: "Erro desconhecido", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, "Erro na resposta da API", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseCadastro>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Falha na conexão", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

// Retrofit Client
object RetrofitClient {
    val apiService: ApiService = Retrofit.Builder()
        .baseUrl("IP")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}
