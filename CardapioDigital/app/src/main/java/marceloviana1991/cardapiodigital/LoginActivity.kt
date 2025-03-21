package marceloviana1991.cardapiodigital

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import marceloviana1991.cardapiodigital.databinding.ActivityLoginBinding
import marceloviana1991.cardapiodigital.dto.LoginRequest
import marceloviana1991.cardapiodigital.http.RetrofitClient
import okio.IOException

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

        val sharedPref = getSharedPreferences("APP_PREFS", MODE_PRIVATE)
        val loginGravado = sharedPref.getString("LOGIN", "") ?: ""
        val senhaGravado = sharedPref.getString("SENHA", "") ?: ""
        if (loginGravado.isNotEmpty()) {
            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.instance.login(LoginRequest(loginGravado, senhaGravado))
                    if (response.isSuccessful) {
                        val token = response.body()?.token
                        sharedPref.edit().putString("TOKEN", token).apply()
                    }
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } catch (e: IOException) {
                    Toast.makeText(this@LoginActivity, "Falha de conexão", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.buttonConfirmar.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val login = binding.editTextLogin.text.toString()
                    val senha = binding.editTextSenha.text.toString()
                    val checkbox = binding.chkKeepMeLogged.isChecked
                    val response = RetrofitClient.instance.login(LoginRequest(login, senha))
                    if (response.isSuccessful) {
                        val token = response.body()?.token
                        sharedPref.edit().putString("TOKEN", token).apply()
                        if (checkbox) {
                            sharedPref.edit().putString("LOGIN", login).apply()
                            sharedPref.edit().putString("SENHA", senha).apply()
                        }
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Falha no login", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: IOException) {
                    Toast.makeText(this@LoginActivity, "Falha de conexão", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, "Erro inesperado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}