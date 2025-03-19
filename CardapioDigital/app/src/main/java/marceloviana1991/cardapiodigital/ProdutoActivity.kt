package marceloviana1991.cardapiodigital

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import marceloviana1991.cardapiodigital.adapter.ProdutoAdapter
import marceloviana1991.cardapiodigital.databinding.ActivityProdutoBinding
import marceloviana1991.cardapiodigital.http.RetrofitClient
import marceloviana1991.cardapiodigital.memory.PedidoMemory
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

        val grupoImagem = intent.getStringExtra("GRUPO_IMAGEM")

        val decodedBytes = Base64.decode(grupoImagem, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        binding.imageView.setImageBitmap(bitmap)

        lifecycleScope.launch {
            val token = sharedPref.getString("TOKEN", "") ?: ""
            val grupoId = sharedPref.getInt("GRUPO_ID", 0)
            try {
                val responseProduto = RetrofitClient.instance.listarProdutosPorGrupo(token, grupoId)
                responseProduto.body()?.let { produtos ->
                    val adapter = ProdutoAdapter(produtos)
                    binding.recyclerview.adapter = adapter
                    binding.buttonConfirmar.setOnClickListener {
                        AlertDialog.Builder(this@ProdutoActivity)
                            .setTitle("Adicionar itens ao pedido")
                            .setMessage("Deseja confirmar operação?")
                            .setPositiveButton("CONFIRMAR" ) { _, _ ->
                                val listaDeItens = adapter.finalizaPedido()
                                listaDeItens.forEach {
                                    PedidoMemory.adicionar(it)
                                }
                                finish()
                            }
                            .setNegativeButton("CANCELAR", null)
                            .show()
                    }
                }

            } catch (e: IOException) {
                Toast.makeText(this@ProdutoActivity, "Falha de conexão", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@ProdutoActivity, "Erro inesperado", Toast.LENGTH_SHORT).show()
            }
        }


    }
}