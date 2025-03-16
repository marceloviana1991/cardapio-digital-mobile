package marceloviana1991.cardapiodigital

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import marceloviana1991.cardapiodigital.databinding.ActivityMainBinding
import marceloviana1991.cardapiodigital.http.RetrofitClient

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

        val token = intent.getStringExtra("token")
        val mainScope = MainScope()
        mainScope.launch {
            try {
                val listaDeGrupos = RetrofitClient.instance.listarGrupos(token!!)
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Falha na requisição", Toast.LENGTH_SHORT).show()
            }

        }
    }
}