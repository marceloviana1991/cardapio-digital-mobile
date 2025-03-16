package marceloviana1991.cardapiodigital

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import marceloviana1991.cardapiodigital.databinding.ActivityMainBinding
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

        lifecycleScope.launch {
            val sharedPref = getSharedPreferences("APP_PREFS", MODE_PRIVATE)
            val token = sharedPref.getString("TOKEN", "") ?: ""
            try {
                val response = RetrofitClient.instance.listarGrupos(token!!)
            } catch (e: IOException) {
                Toast.makeText(this@MainActivity, "Falha de conexão", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Erro inesperado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}