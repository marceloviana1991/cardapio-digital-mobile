package marceloviana1991.cardapiodigital.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import marceloviana1991.cardapiodigital.R
import marceloviana1991.cardapiodigital.dto.ProdutoResponse
import marceloviana1991.cardapiodigital.memory.ItemPedido

class ProdutoAdapter(
    private val produtos: List<ProdutoResponse>
) : RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>() {

    private val itensPedido: MutableList<ItemPedido> = ArrayList()



    class ProdutoViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.textView_nome_produto)
        val descricao: TextView = itemView.findViewById(R.id.textView_descricao_produto)
        val preco: TextView = itemView.findViewById(R.id.textView_preco_produto)
        val adicionar: ImageView = itemView.findViewById(R.id.imageView_adicionar_produto)
        val remover: ImageView = itemView.findViewById(R.id.imageView_remover_produto)
        val quantidade: TextView = itemView.findViewById(R.id.textView_quantidade_produto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_produto, parent, false)
        return ProdutoViewHolder(view)
    }

    override fun getItemCount() = produtos.size

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        val produto = produtos[position]
        var quantidade = 0
        holder.nome.text = produto.nome
        holder.descricao.text = produto.descricao
        holder.preco.text = "R$ ${produto.valor}"
        holder.quantidade.text = quantidade.toString()

        holder.adicionar.setOnClickListener {
            if (quantidade==0) {
                this.itensPedido.add(ItemPedido(quantidade+1, produto.id.toInt()))
            } else {
                this.itensPedido.remove(ItemPedido(quantidade, produto.id.toInt()))
                this.itensPedido.add(ItemPedido(quantidade+1, produto.id.toInt()))
            }
            quantidade += 1
            holder.quantidade.text = quantidade.toString()
        }

        holder.remover.setOnClickListener {
            if (quantidade > 0) {
                quantidade -= 1
                if (quantidade==0) {
                    this.itensPedido.remove(ItemPedido(quantidade, produto.id.toInt()))
                } else {
                    this.itensPedido.remove(ItemPedido(quantidade, produto.id.toInt()))
                    this.itensPedido.add(ItemPedido(quantidade, produto.id.toInt()))
                }
                holder.quantidade.text = quantidade.toString()
            }
        }
    }

    fun finalizaPedido(): List<ItemPedido> {
        return itensPedido
    }
}