package marceloviana1991.cardapiodigital

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import marceloviana1991.cardapiodigital.databinding.ActivityProdutoBinding
import marceloviana1991.cardapiodigital.http.RetrofitClient
import okio.IOException

class ProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityProdutoBinding.inflate(layoutInflater)
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

        val grupoId = intent.getIntExtra("GRUPO", 0)

        lifecycleScope.launch {
            val token = sharedPref.getString("TOKEN", "") ?: ""
            try {
                val responseProduto = RetrofitClient.instance.listarProdutosPorGrupo(token, grupoId)
                responseProduto.body()?.let { produtos ->

                }
            } catch (e: IOException) {
                Toast.makeText(this@ProdutoActivity, "Falha de conexão", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@ProdutoActivity, "Erro inesperado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}