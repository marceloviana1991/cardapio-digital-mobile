package marceloviana1991.cardapiodigital.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import marceloviana1991.cardapiodigital.R
import marceloviana1991.cardapiodigital.dto.GrupoResponse

class GrupoAdapter(
    private val grupos: List<GrupoResponse>
) : RecyclerView.Adapter<GrupoAdapter.GrupoViewHolder>() {

    class GrupoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.textViewGrupo)
        val imagem: ImageView = itemView.findViewById(R.id.imageViewGrupo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrupoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_grupo, parent, false)
        return GrupoViewHolder(view)
    }

    override fun getItemCount(): Int = grupos.size

    override fun onBindViewHolder(holder: GrupoViewHolder, position: Int) {
        val grupo = grupos[position]
        holder.nome.setText(grupo.nome)
        holder.imagem.load(grupo.imagem)
    }
}