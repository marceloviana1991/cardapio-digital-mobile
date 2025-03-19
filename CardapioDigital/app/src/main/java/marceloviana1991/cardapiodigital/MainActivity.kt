package marceloviana1991.cardapiodigital

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import marceloviana1991.cardapiodigital.adapter.GrupoAdapter
import marceloviana1991.cardapiodigital.databinding.ActivityMainBinding
import marceloviana1991.cardapiodigital.http.RetrofitClient
import marceloviana1991.cardapiodigital.memory.PedidoMemory
import okio.IOException

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @SuppressLint("SuspiciousIndentation")
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
                        val adapter = GrupoAdapter(grupos)
                        binding.recyclerview.adapter = adapter
                        adapter.onClickListener = { grupo ->
                            val intent = Intent(
                                this@MainActivity, ProdutoActivity::class.java
                            ).apply {
                                sharedPref.edit().putInt("GRUPO_ID", grupo.id.toInt()).apply()
                                putExtra("GRUPO_IMAGEM", grupo.imagem)
                            }
                            startActivity(intent)
                        }
                }
            } catch (e: IOException) {
                Toast.makeText(this@MainActivity, "Falha de conexão", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Erro inesperado", Toast.LENGTH_SHORT).show()
            }
        }

        binding.floatingActionButtonCancelar.setOnClickListener {
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Exckuir itens ao pedido")
                .setMessage("Deseja confirmar operação?")
                .setPositiveButton("CONFIRMAR" ) { _, _ ->
                    PedidoMemory.limpar()
                }
                .setNegativeButton("CANCELAR", null)
                .show()
        }

        binding.floatingActionButtonConfirmar.setOnClickListener {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Adicionar itens ao pedido")
                    .setMessage("Deseja confirmar operação?")
                    .setPositiveButton("CONFIRMAR" ) { _, _ ->
                        val token = sharedPref.getString("TOKEN", "") ?: ""
                        val pedidosEmMemoria = PedidoMemory.registrar()
                        lifecycleScope.launch {
                            try {
                                // Chamada de Retrofit suspensa
                                val response =
                                    RetrofitClient.instance.registrarPedido(token, pedidosEmMemoria)

                                // Verificando a resposta
                                if (response.isSuccessful) {
                                    // Se o pedido foi registrado com sucesso
                                    PedidoMemory.limpar() // Limpa os pedidos em memória
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Pedido registrado com sucesso",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    // Se ocorreu um erro ao registrar
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Erro ao registrar o pedido",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } catch (e: Exception) {
                                // Lidar com erro de rede ou outra falha
                                Toast.makeText(
                                    this@MainActivity,
                                    "Erro de conexão: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                    }
                    .setNegativeButton("CANCELAR", null)
                    .show()

            }
        }
    }

