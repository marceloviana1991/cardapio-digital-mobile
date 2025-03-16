package marceloviana1991.cardapiodigital

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import marceloviana1991.cardapiodigital.databinding.ActivityLoginBinding
import marceloviana1991.cardapiodigital.dto.LoginRequest
import marceloviana1991.cardapiodigital.http.RetrofitClient

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buttonConfirmar.setOnClickListener {

            val mainScope = MainScope()
            mainScope.launch {
                try {
                    val login = binding.editTextLogin.text.toString()
                    val senha = binding.editTextSenha.text.toString()
                    val token = RetrofitClient.instance.login(LoginRequest(login, senha))
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("token", token.token)
                    startActivity(intent)
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, "Falha no login", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }

}