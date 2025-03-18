package marceloviana1991.cardapiodigital

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import marceloviana1991.cardapiodigital.adapter.GrupoAdapter
import marceloviana1991.cardapiodigital.databinding.ActivityMainBinding
import marceloviana1991.cardapiodigital.dto.GrupoResponse
import marceloviana1991.cardapiodigital.http.RetrofitClient
import okio.IOException

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
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

        binding.imageButton.setOnClickListener {
            sharedPref.edit().putString("LOGIN", "").apply()
            sharedPref.edit().putString("SENHA", "").apply()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        lifecycleScope.launch {
            val token = sharedPref.getString("TOKEN", "") ?: ""
            try {
                val responseGrupo = RetrofitClient.instance.listarGrupos(token)
                    responseGrupo.body()?.let { grupos ->
                    binding.recyclerview.adapter =
                        GrupoAdapter(grupos, GrupoAdapter.OnClickListener { grupo ->
                            val intent = Intent(this@MainActivity, ProdutoActivity::class.java)
                            intent.putExtra("GRUPO", grupo.id)
                            startActivity(intent)
                        })
                }
            } catch (e: IOException) {
                Toast.makeText(this@MainActivity, "Falha de conexão", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Erro inesperado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}